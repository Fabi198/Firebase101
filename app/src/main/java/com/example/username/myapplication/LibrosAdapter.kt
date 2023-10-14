package com.example.username.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.username.myapplication.databinding.ItemLibroBinding

class LibrosAdapter(private var libros: List<Libro>, private val onClick: (Libro, TextView, TextView) -> Unit): RecyclerView.Adapter<LibrosAdapter.LibrosViewHolder>() {

    fun setNuevosLibros(nuevosLibros: List<Libro>) {
        libros = nuevosLibros
    }


    inner class LibrosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val binding = ItemLibroBinding.bind(itemView)

        fun bind(libro: Libro) {
            binding.txtNombre.text = libro.nombre
            binding.txtAutor.text = libro.autor
            binding.cvLibro.setOnClickListener {
                onClick(libro, binding.txtNombre, binding.txtAutor)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrosViewHolder {
        return LibrosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false))
    }

    override fun getItemCount(): Int {
        return libros.size
    }

    override fun onBindViewHolder(holder: LibrosViewHolder, position: Int) {
        holder.bind(libros[position])
    }
}