package com.example.librolink

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BookAdapter(private val context: Context, private val books: List<Book>) : BaseAdapter() {

    override fun getCount(): Int = books.size

    override fun getItem(position: Int): Any = books[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)

        val book = books[position]

        val title = view.findViewById<TextView>(R.id.bookTitle)
        val author = view.findViewById<TextView>(R.id.bookAuthor)
        val image = view.findViewById<ImageView>(R.id.bookImage)

        title.text = book.title
        author.text = book.author

        // Usamos Glide para cargar imágenes
        Glide.with(context).load(book.imageUrl).into(image)

        return view
    }
}
