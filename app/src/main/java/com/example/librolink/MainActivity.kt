package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.editEmail)
        val pass = findViewById<EditText>(R.id.editPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val signupBtn = findViewById<Button>(R.id.btnSignup) // botón de registro

        // 👉 LOGIN local simulado
        loginBtn.setOnClickListener {
            val userEmail = email.text.toString()
            val userPass = pass.text.toString()

            if (userEmail.isNotEmpty() && userPass.isNotEmpty()) {
                // Aquí podrías guardar en SharedPreferences luego
                Toast.makeText(this, "Login exitoso (simulado)", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Por favor ingresa email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // 👉 REGISTRO
        signupBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
