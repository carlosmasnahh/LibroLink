package com.example.librolink

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.librolink.data.DbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : BaseActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: BookAdapter
    private var books: List<Book> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Navegación inferior
        setupBottomNavigation(HomeActivity::class.java)

        // GridView
        gridView = findViewById(R.id.gridBooks)

        // Cargar libros reales
        loadBooks()

        // Click a tarjeta → detalles
        gridView.setOnItemClickListener { _, _, position, _ ->
            val book = books[position]
            val intent = Intent(this, BookDetailActivity::class.java).apply {
                putExtra("title", book.title)
                putExtra("author", book.author)
                putExtra("imageUrl", book.imageUrl)
            }
            startActivity(intent)
        }

        // Botón agregar libro
        findViewById<ImageButton>(R.id.btnAddBook).setOnClickListener {
            startActivity(Intent(this, PublicarLibroActivity::class.java))
        }

        // Botón buscar libro
        findViewById<ImageButton>(R.id.btnSearchBooks).setOnClickListener {
            openSearchDialog()
        }
    }

    // ------------------------------------------------------------------
    // Cargar libros de la BD EXCLUYENDO los libros del usuario logueado
    // ------------------------------------------------------------------
    private fun loadBooks() {
        val userId = Session.getUserId(this)
        if (userId == null) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show()
            return
        }

        val db = DbProvider.get(this)

        lifecycleScope.launch {
            val libroEntities = withContext(Dispatchers.IO) {
                db.libroDao().getBooksFromOtherUsers(userId)
            }

            // Convertir a clase Book que usa tu adapter
            books = libroEntities.map {
                Book(
                    it.Titulo,
                    it.Autor,
                    it.Portada
                )
            }

            adapter = BookAdapter(this@HomeActivity, books)
            gridView.adapter = adapter
        }
    }

    // ---------------------------------------------
    // Cuadro de búsqueda
    // ---------------------------------------------
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

    // ---------------------------------------------
    // Filtro de búsqueda
    // ---------------------------------------------
    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            adapter.updateList(books)
            return
        }

        val filtered = books.filter {
            it.title.contains(query, true) ||
                    (it.author ?: "").contains(query, true)
        }

        if (filtered.isEmpty()) {
            Toast.makeText(this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show()
        }

        adapter.updateList(filtered)
    }

}
