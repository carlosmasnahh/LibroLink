package com.example.librolink

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import android.content.Intent

class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val image = findViewById<ImageView>(R.id.bookDetailImage)
        val title = findViewById<TextView>(R.id.bookDetailTitle)
        val author = findViewById<TextView>(R.id.bookDetailAuthor)
        val condition = findViewById<TextView>(R.id.bookDetailCondition)
        val owner = findViewById<TextView>(R.id.bookDetailOwner)
        val btnRequest = findViewById<Button>(R.id.btnRequest)

        // Recuperamos los datos del Intent
        val bookTitle = intent.getStringExtra("title")
        val bookAuthor = intent.getStringExtra("author")
        val bookImage = intent.getStringExtra("imageUrl")

        title.text = bookTitle
        author.text = bookAuthor
        condition.text = "New"
        owner.text = "Carlos"

        Glide.with(this).load(bookImage).into(image)

        btnRequest.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("title", bookTitle)
                putExtra("author", bookAuthor)
            }
            startActivity(intent)
        }

    }
}
