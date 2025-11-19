package com.example.librolink.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.librolink.data.dao.*
import com.example.librolink.data.entities.*

@Database(
    entities = [
        Usuario::class, Libro::class, Intercambio::class,
        Comunicacion::class, Reputacion::class, Reporte::class, Notificacion::class
    ],
    version = 3,
    exportSchema = false
)
abstract class LibroLinkDb : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun libroDao(): LibroDao
    abstract fun intercambioDao(): IntercambioDao
    abstract fun comunicacionDao(): ComunicacionDao
    abstract fun reputacionDao(): ReputacionDao
    abstract fun reporteDao(): ReporteDao
    abstract fun notificacionDao(): NotificacionDao
}
