package com.example.librolink

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class ChatActivity : BaseActivity() {

    private lateinit var chatContainer: LinearLayout
    private lateinit var chatInput: EditText
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Configurar la navegación inferior
        setupBottomNavigation(ChatActivity::class.java)

        // Inicializar vistas
        chatContainer = findViewById(R.id.chatMessages)
        chatInput = findViewById(R.id.chatInput)
        btnSend = findViewById(R.id.btnSend)

        // Obtener el título del libro si se pasa por intent
        val bookTitle = intent.getStringExtra("title")
        title = "Chat - ${bookTitle ?: "Libro"}"

        // Acción del botón enviar
        btnSend.setOnClickListener {
            val message = chatInput.text.toString().trim()
            if (message.isNotEmpty()) {
                addMessage("Tú: $message")
                chatInput.text.clear()
            }
        }
    }

    private fun addMessage(message: String) {
        val textView = TextView(this)
        textView.text = message
        chatContainer.addView(textView)
    }
}
