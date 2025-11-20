package com.example.librolink.ui.notificaciones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librolink.Session
import com.example.librolink.data.DbProvider
import com.example.librolink.data.repository.NotificacionRepository
import com.example.librolink.databinding.ActivityNotificacionesBinding
import com.example.librolink.viewmodel.NotificacionViewModel
import com.example.librolink.viewmodel.NotificacionViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificacionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionesBinding
    private lateinit var viewModel: NotificacionViewModel
    private lateinit var adapter: NotificacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Recycler
        binding.recyclerNotificaciones.layoutManager = LinearLayoutManager(this)

        // Repo + ViewModel
        val repo = NotificacionRepository(DbProvider.get(this).notificacionDao())
        val factory = NotificacionViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[NotificacionViewModel::class.java]

        adapter = NotificacionAdapter { id ->
            viewModel.marcarLeida(id)
        }

        binding.recyclerNotificaciones.adapter = adapter

        lifecycleScope.launch {
            viewModel.lista.collectLatest { lista ->
                if (lista.isEmpty()) {
                    binding.textEstado.text = "No tienes notificaciones"
                    binding.textEstado.visibility = android.view.View.VISIBLE
                } else {
                    binding.textEstado.visibility = android.view.View.GONE
                }

                adapter.submitList(lista)
            }
        }

        val userId = Session.getUserId(this) ?: 0L
        viewModel.cargar(userId)
    }
}
