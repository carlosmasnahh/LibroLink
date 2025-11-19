package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton
import android.widget.Toast
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

class HomeActivity : BaseActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: BookAdapter
    private lateinit var books: List<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializar bottom navigation
        setupBottomNavigation(HomeActivity::class.java)

        // Inicializar GridView
        gridView = findViewById(R.id.gridBooks)

        books = listOf(
            Book("El Quijote", "Cervantes", "https://picsum.photos/200/300?random=1"),
            Book("Cien años de soledad", "Gabriel García Márquez", "https://picsum.photos/200/300?random=2"),
            Book("La Odisea", "Homero", "https://picsum.photos/200/300?random=3"),
            Book("1984", "George Orwell", "https://picsum.photos/200/300?random=4"),
            Book("Orgullo y prejuicio", "Jane Austen", "https://picsum.photos/200/300?random=5"),
            Book("Hamlet", "Shakespeare", "https://picsum.photos/200/300?random=6")
        )

        adapter = BookAdapter(this, books)
        gridView.adapter = adapter

        // Click de tarjeta → detalles
        gridView.setOnItemClickListener { _, _, position, _ ->
            val book = books[position]
            val intent = Intent(this, BookDetailActivity::class.java).apply {
                putExtra("title", book.title)
                putExtra("author", book.author)
                putExtra("imageUrl", book.imageUrl)
            }
            startActivity(intent)
        }

        // -------------------------------------------------------
        // BOTÓN: Publicar libro (aún sin lógica)
        // -------------------------------------------------------
        findViewById<ImageButton>(R.id.btnAddBook)?.setOnClickListener {
            Toast.makeText(this, "Función de publicar libro próximamente", Toast.LENGTH_SHORT).show()
        }

        // -------------------------------------------------------
        // BOTÓN: Buscar libros
        // -------------------------------------------------------
        findViewById<ImageButton>(R.id.btnSearchBooks)?.setOnClickListener {
            openSearchDialog()
        }
    }

    // ------------------------------
    // Cuadro de búsqueda
    // ------------------------------
    private fun openSearchDialog() {
        val dialog = android.app.AlertDialog.Builder(this)
        dialog.setTitle("Buscar libros")

        val input = EditText(this)
        input.hint = "Buscar por nombre o autor"
        dialog.setView(input)

        dialog.setPositiveButton("Buscar") { _, _ ->
            val query = input.text.toString().trim()
            performSearch(query)
        }

        dialog.setNegativeButton("Cancelar", null)
        dialog.show()
    }

    // ------------------------------
    // Filtro de búsqueda
    // ------------------------------
    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            adapter.updateList(books)
            return
        }

        val filtered = books.filter {
            it.title.contains(query, true) ||
                    it.author.contains(query, true)
        }

        if (filtered.isEmpty()) {
            Toast.makeText(this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show()
        }

        adapter.updateList(filtered)
    }
}
