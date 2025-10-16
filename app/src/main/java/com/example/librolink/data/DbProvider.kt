package com.example.librolink.data

import android.content.Context
import androidx.room.Room

object DbProvider {
    @Volatile private var INSTANCE: LibroLinkDb? = null

    fun get(context: Context): LibroLinkDb =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LibroLinkDb::class.java,
                "librolink.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
}
