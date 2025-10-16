package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Reporte

@Dao
interface ReporteDao {
    @Insert suspend fun insert(reporte: Reporte): Long

    @Query("SELECT * FROM Reporte WHERE ID_Usuario_Reportado = :userId ORDER BY Fecha DESC")
    suspend fun reportesContra(userId: Long): List<Reporte>
}
