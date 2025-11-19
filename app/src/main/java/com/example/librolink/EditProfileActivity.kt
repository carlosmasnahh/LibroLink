package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.librolink.data.DbProvider
import com.example.librolink.data.entities.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileActivity : BaseActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etDni: EditText
    private lateinit var btnGuardar: Button
    private var usuarioId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Barra inferior (si quieres mostrarla aquí también)
        setupBottomNavigation(EditProfileActivity::class.java)

        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etCorreo = findViewById(R.id.etCorreo)
        etDni = findViewById(R.id.etDni)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener id de la sesión (si no se pasa por intent)
        val sid = Session.getUserId(this)
        if (sid == null) {
            Toast.makeText(this, "Sesión expirada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        usuarioId = sid

        // Cargar datos actuales
        val db = DbProvider.get(this)
        lifecycleScope.launch {
            val usuario: Usuario? = withContext(Dispatchers.IO) { db.usuarioDao().findById(usuarioId) }
            if (usuario != null) {
                etNombre.setText(usuario.Nombre)
                etApellido.setText(usuario.Apellido)
                etCorreo.setText(usuario.Correo)
                etDni.setText(usuario.Dni)
            }
        }

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val correo = etCorreo.text.toString().trim().lowercase()
            val dni = etDni.text.toString().trim()

            // Validaciones simples
            when {
                nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || dni.isEmpty() -> {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
                dni.length != 8 || !dni.all { it.isDigit() } -> {
                    Toast.makeText(this, "DNI inválido (8 dígitos)", Toast.LENGTH_SHORT).show()
                }
                !correo.contains("@") -> {
                    Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    lifecycleScope.launch {
                        // Verificar que correo/DNI no pertenezcan a otro usuario
                        val dbInstance = DbProvider.get(this@EditProfileActivity)
                        val existeCorreo = withContext(Dispatchers.IO) {
                            dbInstance.usuarioDao().findByCorreo(correo)
                        }
                        if (existeCorreo != null && existeCorreo.ID_Usuario != usuarioId) {
                            runOnUiThread {
                                Toast.makeText(this@EditProfileActivity, "El correo ya está en uso", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }

                        val existeDni = withContext(Dispatchers.IO) {
                            dbInstance.usuarioDao().findByDni(dni)
                        }
                        if (existeDni != null && existeDni.ID_Usuario != usuarioId) {
                            runOnUiThread {
                                Toast.makeText(this@EditProfileActivity, "El DNI ya está en uso", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }

                        // Actualizar usuario en BD
                        withContext(Dispatchers.IO) {
                            val usuarioActual = dbInstance.usuarioDao().findById(usuarioId)
                            if (usuarioActual != null) {
                                val actualizado = Usuario(
                                    ID_Usuario = usuarioActual.ID_Usuario,
                                    Nombre = nombre,
                                    Apellido = apellido,
                                    Dni = dni,
                                    Correo = correo,
                                    Contrasena = usuarioActual.Contrasena,
                                    Ubicacion = usuarioActual.Ubicacion,
                                    Reputacion = usuarioActual.Reputacion
                                )
                                dbInstance.usuarioDao().update(actualizado)
                            }
                        }

                        runOnUiThread {
                            Toast.makeText(this@EditProfileActivity, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                            // Volver a ProfileActivity y refrescar
                            startActivity(Intent(this@EditProfileActivity, ProfileActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            })
                            finish()
                        }
                    }
                }
            }
        }
    }
}
