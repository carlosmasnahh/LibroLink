package com.example.librolink.data.entities

import androidx.room.*

@Entity(
    tableName = "Reputacion",
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Emisor"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Receptor"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("ID_Usuario_Receptor")]
)
data class Reputacion(
    @PrimaryKey(autoGenerate = true) val ID_Reputacion: Long = 0,
    val ID_Usuario_Emisor: Long,
    val ID_Usuario_Receptor: Long,
    val Calificacion: Int,   // 1..5
    val Comentario: String? = null,
    val Fecha: String
)
