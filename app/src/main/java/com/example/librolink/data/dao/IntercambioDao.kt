package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Intercambio

@Dao
interface IntercambioDao {
    @Insert suspend fun insert(intercambio: Intercambio): Long
    @Update suspend fun update(intercambio: Intercambio)

    @Query("""
        SELECT * FROM Intercambio
        WHERE ID_Usuario_Solicitante = :userId OR ID_Usuario_Propietario = :userId
        ORDER BY FechaInicio DESC
    """)
    suspend fun intercambiosDeUsuario(userId: Long): List<Intercambio>

    @Query("UPDATE Intercambio SET Estado = :estado, FechaFin = :fechaFin WHERE ID_Intercambio = :id")
    suspend fun actualizarEstado(id: Long, estado: String, fechaFin: String?)
}
