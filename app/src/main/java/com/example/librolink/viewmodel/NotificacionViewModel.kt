package com.example.librolink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.librolink.data.entities.Notificacion
import com.example.librolink.data.repository.NotificacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificacionViewModel(private val repo: NotificacionRepository) : ViewModel() {

    private val _lista = MutableStateFlow<List<Notificacion>>(emptyList())
    val lista: StateFlow<List<Notificacion>> = _lista

    fun cargar(userId: Long) {
        viewModelScope.launch {
            _lista.value = repo.listar(userId)
        }
    }

    fun marcarLeida(id: Long) {
        viewModelScope.launch {
            repo.marcarLeida(id)

            val usuarioId = _lista.value.firstOrNull()?.ID_Usuario
            if (usuarioId != null) {
                cargar(usuarioId)
            }
        }
    }
}

// ---------------------------------------------------------
// FACTORY NECESARIA PARA CREAR EL VIEWMODEL DESDE UN FRAGMENT
// ---------------------------------------------------------
class NotificacionViewModelFactory(
    private val repo: NotificacionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificacionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificacionViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
