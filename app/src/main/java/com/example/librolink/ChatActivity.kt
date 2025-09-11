package com.example.librolink

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    private lateinit var chatContainer: LinearLayout
    private lateinit var chatInput: EditText
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatContainer = findViewById(R.id.chatMessages)
        chatInput = findViewById(R.id.chatInput)
        btnSend = findViewById(R.id.btnSend)

        val bookTitle = intent.getStringExtra("title")
        title = "Chat - $bookTitle"

        btnSend.setOnClickListener {
            val message = chatInput.text.toString()
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
