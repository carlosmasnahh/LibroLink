package com.example.librolink

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Configurar la navegación inferior
        setupBottomNavigation(MapActivity::class.java)

        // Inicializar vistas
        val btnFindUsers = findViewById<Button>(R.id.btnFindUsers)

        btnFindUsers.setOnClickListener {
            // Aquí puedes agregar la acción para buscar usuarios o mostrar el mapa
        }
    }
}
