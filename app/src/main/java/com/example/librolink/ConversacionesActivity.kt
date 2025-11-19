package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import com.example.librolink.adapters.UsuarioAdapter
import com.example.librolink.data.DbProvider
import com.example.librolink.data.entities.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConversacionesActivity : BaseActivity() {

    private lateinit var inputBuscar: EditText
    private lateinit var listaBusqueda: ListView
    private lateinit var listaConversaciones: ListView

    private lateinit var adapterBusqueda: UsuarioAdapter
    private lateinit var adapterConversaciones: UsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversaciones)

        // Barra inferior
        setupBottomNavigation(ConversacionesActivity::class.java)

        inputBuscar = findViewById(R.id.inputBuscarUsuario)
        listaBusqueda = findViewById(R.id.listaBusqueda)
        listaConversaciones = findViewById(R.id.listaConversaciones)

        adapterBusqueda = UsuarioAdapter(this, mutableListOf())
        adapterConversaciones = UsuarioAdapter(this, mutableListOf())

        listaBusqueda.adapter = adapterBusqueda
        listaConversaciones.adapter = adapterConversaciones

        val db = DbProvider.get(this)
        val dao = db.usuarioDao()

        // Cargar usuarios
        CoroutineScope(Dispatchers.IO).launch {
            val usuarios = dao.obtenerTodos()
            runOnUiThread {
                adapterConversaciones.actualizarLista(usuarios)
            }
        }

        // Click en lista de búsqueda
        listaBusqueda.setOnItemClickListener { _, _, position, _ ->
            val usuario = adapterBusqueda.getItem(position) as Usuario
            abrirChat(usuario)
        }

        // Click en lista de conversaciones
        listaConversaciones.setOnItemClickListener { _, _, position, _ ->
            val usuario = adapterConversaciones.getItem(position) as Usuario
            abrirChat(usuario)
        }

        // Buscador
        inputBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val query = text.toString().trim()

                if (query.isEmpty()) {
                    adapterBusqueda.actualizarLista(emptyList())
                    return
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val resultados = dao.buscarUsuariosPorNombreOApellido("%$query%")
                    runOnUiThread {
                        adapterBusqueda.actualizarLista(resultados)
                    }
                }
            }
        })
    }

    // Abrir ChatActivity con los datos correctos
    private fun abrirChat(usuario: Usuario) {
        val intent = Intent(this, ChatActivity::class.java)

        intent.putExtra("usuarioId", usuario.ID_Usuario)
        intent.putExtra("usuarioNombre", usuario.Nombre)
        intent.putExtra("usuarioApellido", usuario.Apellido)

        startActivity(intent)
    }
}
