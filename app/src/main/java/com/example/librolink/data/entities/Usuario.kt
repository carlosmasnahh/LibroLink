package com.example.librolink.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Usuario",
    indices = [Index(value = ["Correo"], unique = true), Index(value = ["Dni"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val ID_Usuario: Long = 0,
    val Nombre: String,
    val Apellido: String,
    val Dni: String,                 // NUEVO (obligatorio)
    val Correo: String,
    val Contrasena: String,
    val Ubicacion: String? = null,
    val Reputacion: Double = 0.0
)
