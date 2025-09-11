package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 🔹 Configurar Toolbar como ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val gridView = findViewById<GridView>(R.id.gridBooks)

        val books = listOf(
            Book("El Quijote", "Cervantes", "https://picsum.photos/200/300?random=1"),
            Book("Cien años de soledad", "Gabriel García Márquez", "https://picsum.photos/200/300?random=2"),
            Book("La Odisea", "Homero", "https://picsum.photos/200/300?random=3"),
            Book("1984", "George Orwell", "https://picsum.photos/200/300?random=4"),
            Book("Orgullo y prejuicio", "Jane Austen", "https://picsum.photos/200/300?random=5"),
            Book("Hamlet", "Shakespeare", "https://picsum.photos/200/300?random=6")
        )

        val adapter = BookAdapter(this, books)
        gridView.adapter = adapter

        // 👉 Click en un libro → abre detalle
        gridView.setOnItemClickListener { _, _, position, _ ->
            val book = books[position]

            val intent = Intent(this, BookDetailActivity::class.java).apply {
                putExtra("title", book.title)
                putExtra("author", book.author)
                putExtra("imageUrl", book.imageUrl)
            }
            startActivity(intent)
        }
    }

    // 🔹 Cargar menú en Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    // 🔹 Acciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
