package com.example.librolink.data.entities

import androidx.room.*

@Entity(
    tableName = "Libro",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["ID_Usuario"],
            childColumns = ["ID_Usuario"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ID_Usuario")]
)
data class Libro(
    @PrimaryKey(autoGenerate = true) val ID_Libro: Long = 0,
    val Titulo: String,
    val Autor: String? = null,
    val Genero: String? = null,
    val Estado: String,              // BUENO|REGULAR|MALO
    val Disponibilidad: Boolean = true,
    val ID_Usuario: Long
)
