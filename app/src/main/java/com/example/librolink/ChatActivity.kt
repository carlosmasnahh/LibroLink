package com.example.librolink

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ChatActivity : BaseActivity() {

    private lateinit var chatContainer: LinearLayout
    private lateinit var chatInput: EditText
    private lateinit var btnSend: Button
    private lateinit var btnBack: ImageView
    private lateinit var txtTitulo: TextView

    private var usuarioId: Int = -1
    private var usuarioNombre: String = ""
    private var usuarioApellido: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // ----- Inicializar vistas -----
        chatContainer = findViewById(R.id.chatMessages)
        chatInput = findViewById(R.id.chatInput)
        btnSend = findViewById(R.id.btnSend)
        btnBack = findViewById(R.id.btnBack)
        txtTitulo = findViewById(R.id.txtTituloChat)

        // Extraer datos del intent
        usuarioId = intent.getIntExtra("usuarioId", -1)
        usuarioNombre = intent.getStringExtra("usuarioNombre") ?: ""
        usuarioApellido = intent.getStringExtra("usuarioApellido") ?: ""

        // Poner el título del chat
        txtTitulo.text = "Chat con $usuarioNombre $usuarioApellido"

        // Acción del botón atrás
        btnBack.setOnClickListener {
            finish()
        }

        // Acción del botón enviar
        btnSend.setOnClickListener {
            val message = chatInput.text.toString().trim()
            if (message.isNotEmpty()) {
                agregarMensajePropio(message)
                chatInput.text.clear()
            }
        }
    }

    // Agregar mensaje en la pantalla (lado del usuario)
    private fun agregarMensajePropio(text: String) {
        val tv = TextView(this)
        tv.text = "Tú: $text"

        // Aquí puedes agregar estilos si quieres (margen, color, tamaño, etc.)
        chatContainer.addView(tv)
    }
}
