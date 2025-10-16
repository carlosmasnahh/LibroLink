package com.example.librolink.data.entities

import androidx.room.*

@Entity(
    tableName = "Intercambio",
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Solicitante"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Usuario::class, parentColumns = ["ID_Usuario"], childColumns = ["ID_Usuario_Propietario"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Libro::class,    parentColumns = ["ID_Libro"],   childColumns = ["ID_Libro"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("ID_Usuario_Solicitante"), Index("ID_Usuario_Propietario"), Index("ID_Libro")]
)
data class Intercambio(
    @PrimaryKey(autoGenerate = true) val ID_Intercambio: Long = 0,
    val FechaInicio: String,      // ISO-8601
    val FechaFin: String? = null,
    val Estado: String,           // PENDIENTE|ACEPTADO|RECHAZADO|EN_CURSO|CERRADO|CANCELADO
    val ID_Usuario_Solicitante: Long,
    val ID_Usuario_Propietario: Long,
    val ID_Libro: Long
)
