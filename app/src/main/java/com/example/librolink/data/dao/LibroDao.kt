package com.example.librolink.data.dao

import androidx.room.*
import com.example.librolink.data.entities.Libro

@Dao
interface LibroDao {
    @Insert suspend fun insert(libro: Libro): Long
    @Update suspend fun update(libro: Libro)
    @Delete suspend fun delete(libro: Libro)

    @Query("SELECT * FROM Libro WHERE ID_Usuario = :userId")
    suspend fun librosDeUsuario(userId: Long): List<Libro>

    @Query("SELECT * FROM Libro WHERE Disponibilidad = 1")
    suspend fun librosDisponibles(): List<Libro>

    @Query("UPDATE Libro SET Disponibilidad = :disp WHERE ID_Libro = :libroId")
    suspend fun setDisponibilidad(libroId: Long, disp: Boolean)

    @Query("SELECT * FROM Libro WHERE ID_Usuario != :userId")
    suspend fun getBooksFromOtherUsers(userId: Long): List<Libro>

}
