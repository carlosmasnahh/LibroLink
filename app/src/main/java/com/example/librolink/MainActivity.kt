package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.librolink.data.DbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si ya hay sesión, ir directo a Home
        Session.getUserId(this)?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.editEmail)
        etPass  = findViewById(R.id.editPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val correo = etEmail.text.toString().trim().lowercase()
            val pass = etPass.text.toString()

            if (correo.isEmpty() || pass.isEmpty()) {
                toast("Ingresa correo y contraseña"); return@setOnClickListener
            }

            val db = DbProvider.get(this)
            lifecycleScope.launch {
                val usuario = withContext(Dispatchers.IO) { db.usuarioDao().findByCorreo(correo) }
                if (usuario == null) { toast("Usuario no encontrado"); return@launch }
                if (usuario.Contrasena != pass) { toast("Contraseña incorrecta"); return@launch }

                Session.saveUserId(this@MainActivity, usuario.ID_Usuario)
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
