package com.example.librolink

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.librolink.data.LibroLinkDb
import com.example.librolink.data.entities.Libro
import com.example.librolink.data.entities.Notificacion
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PublicarLibroActivity : AppCompatActivity() {

    private lateinit var db: LibroLinkDb
    private var portadaUri: Uri? = null
    private val PICK_IMAGE_CODE = 2001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicar_libro)

        // BD
        db = Room.databaseBuilder(
            applicationContext,
            LibroLinkDb::class.java,
            "librolink.db"
        ).build()

        // ID del usuario
        val userId = Session.getUserId(this)
        if (userId == null) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Views
        val edtTitulo = findViewById<EditText>(R.id.edtTituloLibro)
        val edtAutor = findViewById<EditText>(R.id.edtAutorLibro)
        val edtGenero = findViewById<EditText>(R.id.edtGeneroLibro)
        val spinnerEstado = findViewById<Spinner>(R.id.spinnerEstado)
        val imgPortada = findViewById<ImageView>(R.id.imgPortadaLibro)
        val btnSubirPortada = findViewById<Button>(R.id.btnSubirPortada)
        val btnPublicar = findViewById<Button>(R.id.btnPublicarLibro)

        // Spinner estado
        val estados = listOf("BUENO", "REGULAR", "MALO")
        spinnerEstado.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)

        // Seleccionar imagen
        btnSubirPortada.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_CODE)
        }

        // Publicar libro
        btnPublicar.setOnClickListener {

            val titulo = edtTitulo.text.toString().trim()
            val autor = edtAutor.text.toString().trim()
            val genero = edtGenero.text.toString().trim()
            val estado = spinnerEstado.selectedItem.toString()
            val portada = portadaUri?.toString() ?: ""

            if (titulo.isEmpty()) {
                edtTitulo.error = "Ingresa un título"
                return@setOnClickListener
            }

            lifecycleScope.launch {

                // Guardar libro
                val libro = Libro(
                    Titulo = titulo,
                    Autor = autor,
                    Genero = genero,
                    Estado = estado,
                    Disponibilidad = true,
                    Portada = portada,
                    ID_Usuario = userId
                )

                db.libroDao().insert(libro)

                // Fecha compatible con API 23
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val fecha = sdf.format(Date())

                // 💬 Notificación PARA EL USUARIO ACTUAL
                val notiUsuario = Notificacion(
                    ID_Usuario = userId,
                    Tipo = "SISTEMA",
                    Contenido = "Libro publicado con éxito",
                    FechaHora = fecha,
                    Estado = "NUEVA"
                )
                db.notificacionDao().insert(notiUsuario)

                // 💬 Notificación PARA LOS OTROS USUARIOS
                val otrosUsuarios = db.usuarioDao().getAllUsersExcept(userId)
                otrosUsuarios.forEach { user ->
                    val notiOtros = Notificacion(
                        ID_Usuario = user.ID_Usuario,
                        Tipo = "INTERCAMBIO",
                        Contenido = "Hay nuevos libros por intercambiar 📚",
                        FechaHora = fecha,
                        Estado = "NUEVA"
                    )
                    db.notificacionDao().insert(notiOtros)
                }

                Toast.makeText(
                    this@PublicarLibroActivity,
                    "Libro publicado con éxito 📚",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            portadaUri = data?.data
            findViewById<ImageView>(R.id.imgPortadaLibro).setImageURI(portadaUri)
        }
    }
}
