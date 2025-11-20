package com.example.librolink.data.repository

import com.example.librolink.data.dao.NotificacionDao
import com.example.librolink.data.entities.Notificacion

class NotificacionRepository(private val dao: NotificacionDao) {

    suspend fun crear(notificacion: Notificacion): Long {
        return dao.insert(notificacion)
    }

    suspend fun listar(userId: Long): List<Notificacion> {
        return dao.listar(userId)
    }

    suspend fun marcarLeida(id: Long) {
        dao.marcarLeida(id)
    }
}
