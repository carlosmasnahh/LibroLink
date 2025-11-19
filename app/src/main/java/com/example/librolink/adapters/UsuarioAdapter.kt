package com.example.librolink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.librolink.R
import com.example.librolink.data.entities.Usuario

class UsuarioAdapter(
    private val context: Context,
    private var usuarios: List<Usuario>
) : BaseAdapter() {

    override fun getCount(): Int = usuarios.size

    override fun getItem(position: Int): Any = usuarios[position]

    override fun getItemId(position: Int): Long = usuarios[position].ID_Usuario

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_usuario_chat, parent, false)

        val usuario = usuarios[position]

        val txtNombre = view.findViewById<TextView>(R.id.txtNombreUsuario)
        txtNombre.text = "${usuario.Nombre} ${usuario.Apellido}"

        return view
    }

    fun actualizarLista(nuevaLista: List<Usuario>) {
        usuarios = nuevaLista
        notifyDataSetChanged()
    }
}
