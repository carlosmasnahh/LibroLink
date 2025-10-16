package com.example.librolink.data.entities

import androidx.room.*

@Entity(
    tableName = "Comunicacion",
    foreignKeys = [
        ForeignKey(entity = Intercambio::class, parentColumns = ["ID_Intercambio"], childColumns = ["ID_Intercambio"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Emisor"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("ID_Intercambio"), Index("ID_Usuario_Emisor")]
)
data class Comunicacion(
    @PrimaryKey(autoGenerate = true) val ID_Comunicacion: Long = 0,
    val ID_Intercambio: Long,
    val ID_Usuario_Emisor: Long,
    val Mensaje: String,
    val FechaHora: String
)
