package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Notificacion

@Dao
interface NotificacionDao {
    @Insert suspend fun insert(n: Notificacion): Long

    @Query("SELECT * FROM Notificacion WHERE ID_Usuario = :userId ORDER BY FechaHora DESC")
    suspend fun listar(userId: Long): List<Notificacion>

    @Query("UPDATE Notificacion SET Estado = 'LEIDA' WHERE ID_Notificacion = :id")
    suspend fun marcarLeida(id: Long)
}
