package com.example.miappevaluacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Date

data class Noticia(
    var id: String = "",
    val titulo: String = "",
    val bajada: String = "",
    val cuerpo: String = "",
    val autor: String = "",
    val fecha: Date? = null,
    val imagenUrl: String = ""
)

class NoticiasAdapter(
    private val listaNoticias: List<Noticia>,
    private val alHacerClic: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloItem)
        val tvBajada: TextView = itemView.findViewById(R.id.tvBajadaItem)
        val tvCuerpo: TextView = itemView.findViewById(R.id.tvCuerpoItem)
        val ivImagen: ImageView = itemView.findViewById(R.id.ivNoticiaItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = listaNoticias[position]

        holder.tvTitulo.text = noticia.titulo
        holder.tvBajada.text = noticia.bajada
        holder.tvCuerpo.text = noticia.cuerpo

        if (noticia.imagenUrl.isNotEmpty()) {
            holder.ivImagen.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(noticia.imagenUrl)
                .into(holder.ivImagen)
        } else {
            holder.ivImagen.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { alHacerClic(noticia) }
    }

    override fun getItemCount(): Int = listaNoticias.size
}