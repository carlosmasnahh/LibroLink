package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.librolink.data.DbProvider
import com.example.librolink.data.entities.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etDni: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPass: EditText
    private lateinit var etPass2: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.editNombre)
        etApellido = findViewById(R.id.editApellido)
        etDni = findViewById(R.id.editDni)
        etCorreo = findViewById(R.id.editEmailRegister)
        etPass = findViewById(R.id.editPasswordRegister)
        etPass2 = findViewById(R.id.editPasswordRegisterConfirm)
        btnRegister = findViewById(R.id.btnRegisterUser)

        btnRegister.setOnClickListener { registrar() }
    }

    private fun registrar() {
        val nombre = etNombre.text.toString().trim()
        val apellido = etApellido.text.toString().trim()
        val dni = etDni.text.toString().trim()
        val correo = etCorreo.text.toString().trim().lowercase()
        val pass = etPass.text.toString()
        val pass2 = etPass2.text.toString()

        // Validaciones
        when {
            nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()
                    || correo.isEmpty() || pass.isEmpty() || pass2.isEmpty() ->
                toast("Completa todos los campos")

            dni.length != 8 || !dni.all { it.isDigit() } ->
                toast("DNI inválido (8 dígitos)")

            !correo.contains("@") ->
                toast("Correo inválido")

            pass.length < 6 ->
                toast("La contraseña debe tener al menos 6 caracteres")

            pass != pass2 ->
                toast("Las contraseñas no coinciden")

            else -> {
                val db = DbProvider.get(this)
                lifecycleScope.launch {
                    val existeCorreo = withContext(Dispatchers.IO) { db.usuarioDao().findByCorreo(correo) }
                    if (existeCorreo != null) { toast("El correo ya está registrado"); return@launch }

                    val existeDni = withContext(Dispatchers.IO) {
                        // Búsqueda simple por DNI
                        db.usuarioDao().findByDni(dni)
                    }
                    if (existeDni != null) { toast("El DNI ya está registrado"); return@launch }

                    val nuevoId = withContext(Dispatchers.IO) {
                        db.usuarioDao().insert(
                            Usuario(
                                Nombre = nombre,
                                Apellido = apellido,
                                Dni = dni,
                                Correo = correo,
                                Contrasena = pass, // para demo (luego: hash)
                                Ubicacion = null
                            )
                        )
                    }
                    Session.saveUserId(this@RegisterActivity, nuevoId)
                    toast("¡Registro exitoso!")
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
