package com.example.librolink.ui.notificaciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librolink.R
import com.example.librolink.Session
import com.example.librolink.data.DbProvider
import com.example.librolink.data.repository.NotificacionRepository
import com.example.librolink.viewmodel.NotificacionViewModel
import com.example.librolink.viewmodel.NotificacionViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificacionesFragment : Fragment() {

    private lateinit var viewModel: NotificacionViewModel
    private lateinit var adapter: NotificacionAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var txtEstado: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notificaciones, container, false)

        txtEstado = root.findViewById(R.id.textEstado)
        recycler = root.findViewById(R.id.recyclerNotificaciones)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Repo + ViewModel
        val repo = NotificacionRepository(DbProvider.get(requireContext()).notificacionDao())
        val factory = NotificacionViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[NotificacionViewModel::class.java]

        adapter = NotificacionAdapter { id ->
            viewModel.marcarLeida(id)
        }
        recycler.adapter = adapter

        // Observar los datos
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.lista.collectLatest { lista ->
                if (lista.isEmpty()) {
                    txtEstado.text = "No tienes notificaciones"
                    txtEstado.visibility = View.VISIBLE
                } else {
                    txtEstado.visibility = View.GONE
                }

                adapter.submitList(lista)
            }
        }

        val userId = Session.getUserId(requireContext()) ?: 0L
        viewModel.cargar(userId)

        return root
    }
}
