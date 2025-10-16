package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Reputacion

@Dao
interface ReputacionDao {
    @Insert suspend fun insert(rep: Reputacion): Long

    @Query("SELECT AVG(Calificacion) FROM Reputacion WHERE ID_Usuario_Receptor = :userId")
    suspend fun promedio(userId: Long): Double?
}
