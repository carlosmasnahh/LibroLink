package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Configura los botones de la barra inferior
    protected fun setupBottomNavigation(currentActivity: Class<*>) {
        findViewById<Button>(R.id.btnHome)?.setOnClickListener {
            if (currentActivity != HomeActivity::class.java) {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            }
        }

        findViewById<Button>(R.id.btnChat)?.setOnClickListener {
            if (currentActivity != ChatActivity::class.java) {
                startActivity(Intent(this, ChatActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            }
        }

        findViewById<Button>(R.id.btnSearch)?.setOnClickListener {
            if (currentActivity != MapActivity::class.java) {
                startActivity(Intent(this, MapActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            }
        }

        findViewById<Button>(R.id.btnProfile)?.setOnClickListener {
            if (currentActivity != ProfileActivity::class.java) {
                startActivity(Intent(this, ProfileActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            }
        }
    }
}
