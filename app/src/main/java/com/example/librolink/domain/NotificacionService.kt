package com.example.librolink.domain

import com.example.librolink.data.entities.Notificacion
import com.example.librolink.data.repository.NotificacionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NotificacionService(private val repo: NotificacionRepository) {

    private fun ahora(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    // -----------------------------------------------
    // NOTIFICACIÓN 1: Al recibir un mensaje
    // -----------------------------------------------
    suspend fun notificarMensaje(recibeId: Long, enviaNombre: String) {
        val n = Notificacion(
            ID_Usuario = recibeId,
            Tipo = "MENSAJE",
            Contenido = "Has recibido un mensaje de $enviaNombre",
            FechaHora = ahora(),
            Estado = "NUEVA"
        )
        repo.crear(n)
    }

    // -----------------------------------------------
    // NOTIFICACIÓN 2: Al publicar un libro (al autor)
    // -----------------------------------------------
    suspend fun notificarLibroPublicado(autorId: Long, titulo: String) {
        val n = Notificacion(
            ID_Usuario = autorId,
            Tipo = "SISTEMA",
            Contenido = "Tu libro '$titulo' fue publicado con éxito",
            FechaHora = ahora(),
            Estado = "NUEVA"
        )
        repo.crear(n)
    }

    // -----------------------------------------------
    // NOTIFICACIÓN 3: A todos los usuarios (nuevos libros)
    // -----------------------------------------------
    suspend fun notificarNuevoLibroATodos(userIds: List<Long>, titulo: String) {
        withContext(Dispatchers.IO) {
            userIds.forEach { id ->
                val n = Notificacion(
                    ID_Usuario = id,
                    Tipo = "NUEVO_LIBRO",
                    Contenido = "Se ha publicado un nuevo libro: '$titulo'",
                    FechaHora = ahora(),
                    Estado = "NUEVA"
                )
                repo.crear(n)
            }
        }
    }
}
