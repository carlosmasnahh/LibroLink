package com.example.librolink.ui.notificaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.librolink.R
import com.example.librolink.data.entities.Notificacion

class NotificacionAdapter(
    private val onClick: (Long) -> Unit
) : ListAdapter<Notificacion, NotificacionAdapter.VH>(Diff()) {

    inner class VH(val v: View) : RecyclerView.ViewHolder(v) {
        val txtContenido: TextView = v.findViewById(R.id.txtContenido)
        val txtFecha: TextView = v.findViewById(R.id.txtFecha)
        val card: CardView = v.findViewById(R.id.cardNotif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notificacion, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val n = getItem(position)

        holder.txtContenido.text = n.Contenido
        holder.txtFecha.text = n.FechaHora

        holder.card.setOnClickListener {
            onClick(n.ID_Notificacion)
        }

        holder.card.alpha = if (n.Estado == "LEIDA") 0.5f else 1f
    }

    class Diff : DiffUtil.ItemCallback<Notificacion>() {
        override fun areItemsTheSame(old: Notificacion, new: Notificacion) =
            old.ID_Notificacion == new.ID_Notificacion

        override fun areContentsTheSame(old: Notificacion, new: Notificacion) =
            old == new
    }
}
