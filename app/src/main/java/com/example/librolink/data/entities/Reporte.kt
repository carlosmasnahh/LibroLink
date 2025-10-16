package com.example.librolink.data.entities

import androidx.room.*

@Entity(
    tableName = "Reporte",
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Emisor"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Reportado"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("ID_Usuario_Reportado")]
)
data class Reporte(
    @PrimaryKey(autoGenerate = true) val ID_Reporte: Long = 0,
    val ID_Usuario_Emisor: Long,
    val ID_Usuario_Reportado: Long,
    val Razon: String,
    val Fecha: String
)
