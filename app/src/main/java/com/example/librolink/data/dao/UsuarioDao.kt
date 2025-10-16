package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Usuario

@Dao
interface UsuarioDao {
    @Insert suspend fun insert(usuario: Usuario): Long
    @Update suspend fun update(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE ID_Usuario = :id LIMIT 1")
    suspend fun findById(id: Long): Usuario?

    @Query("SELECT * FROM Usuario WHERE Correo = :correo LIMIT 1")
    suspend fun findByCorreo(correo: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE Dni = :dni LIMIT 1")
    suspend fun findByDni(dni: String): Usuario?

    @Query("UPDATE Usuario SET Reputacion = :nueva WHERE ID_Usuario = :userId")
    suspend fun actualizarReputacion(userId: Long, nueva: Double)
}
