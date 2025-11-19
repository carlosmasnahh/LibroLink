package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.librolink.data.DbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : BaseActivity() {

    private lateinit var tvNombreCompleto: TextView
    private lateinit var tvCorreo: TextView
    private lateinit var tvDni: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnEditarPerfil: Button
    private lateinit var imgPerfil: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = Session.getUserId(this)
        if (userId == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_profile)
        setupBottomNavigation(ProfileActivity::class.java)

        imgPerfil = findViewById(R.id.imgPerfil)
        tvNombreCompleto = findViewById(R.id.tvNombreCompleto)
        tvCorreo = findViewById(R.id.tvCorreo)
        tvDni = findViewById(R.id.tvDni)
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil)
        btnLogout = findViewById(R.id.btnLogout)

        val db = DbProvider.get(this)

        lifecycleScope.launch {
            val usuario = withContext(Dispatchers.IO) { db.usuarioDao().findById(userId) }

            if (usuario == null) {
                Toast.makeText(this@ProfileActivity, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                Session.clear(this@ProfileActivity)
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                finish()
                return@launch
            }

            tvNombreCompleto.text = "${usuario.Nombre} ${usuario.Apellido}"
            tvCorreo.text = "Correo: ${usuario.Correo}"
            tvDni.text = "DNI: ${usuario.Dni}"
        }

        btnEditarPerfil.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        btnLogout.setOnClickListener {
            Session.clear(this)
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}
