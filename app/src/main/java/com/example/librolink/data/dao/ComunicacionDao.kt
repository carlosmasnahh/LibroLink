package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Comunicacion

@Dao
interface ComunicacionDao {
    @Insert suspend fun insert(msg: Comunicacion): Long

    @Query("SELECT * FROM Comunicacion WHERE ID_Intercambio = :interId ORDER BY FechaHora ASC")
    suspend fun mensajes(interId: Long): List<Comunicacion>
}
