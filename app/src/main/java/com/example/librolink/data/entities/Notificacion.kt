package com.example.librolink.data.entities

import androidx.room.*

@Entity(
    tableName = "Notificacion",
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("ID_Usuario")]
)
data class Notificacion(
    @PrimaryKey(autoGenerate = true) val ID_Notificacion: Long = 0,
    val ID_Usuario: Long,
    val Tipo: String,           // INTERCAMBIO|MENSAJE|SISTEMA, etc.
    val Contenido: String,
    val FechaHora: String,
    val Estado: String          // NUEVA|LEIDA
)
