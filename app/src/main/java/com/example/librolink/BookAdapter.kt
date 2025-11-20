package com.example.librolink

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BookAdapter(
    private val context: Context,
    private var books: List<Book>
) : BaseAdapter() {

    override fun getCount(): Int = books.size

    override fun getItem(position: Int): Any = books[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)

        val book = books[position]

        val titleView = view.findViewById<TextView>(R.id.bookTitle)
        val authorView = view.findViewById<TextView>(R.id.bookAuthor)
        val imageView = view.findViewById<ImageView>(R.id.bookImage)

        titleView.text = book.title
        authorView.text = book.author ?: "Autor desconocido"

        // -----------------------------
        // Glide con icono por defecto
        // -----------------------------
        Glide.with(context)
            .load(book.imageUrl)
            .placeholder(R.mipmap.ic_launcher)   // imagen mientras carga
            .error(R.mipmap.ic_launcher)         // imagen si falla la carga
            .into(imageView)

        return view
    }

    fun updateList(newList: List<Book>) {
        books = newList
        notifyDataSetChanged()
    }
}
