package com.example.miappevaluacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class VerNoticiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_noticia)

        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val tvBajada = findViewById<TextView>(R.id.tvBajadaDetalle)
        val tvAutor = findViewById<TextView>(R.id.tvAutorDetalle)
        val tvCuerpo = findViewById<TextView>(R.id.tvCuerpoDetalle)
        val ivImagen = findViewById<ImageView>(R.id.ivImagenDetalle)

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        val idNoticia = intent.getStringExtra("idNoticia")
        val titulo = intent.getStringExtra("titulo")
        val bajada = intent.getStringExtra("bajada")
        val cuerpo = intent.getStringExtra("cuerpo")
        val autor = intent.getStringExtra("autor")
        val imagenUrl = intent.getStringExtra("imagenUrl")

        tvTitulo.text = titulo
        tvBajada.text = bajada
        tvAutor.text = "Por: $autor"
        tvCuerpo.text = cuerpo

        if (!imagenUrl.isNullOrEmpty()) {
            Glide.with(this).load(imagenUrl).into(ivImagen)
        }

        btnVolver.setOnClickListener {
            finish()
        }

        btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Eliminar Noticia")
            builder.setMessage("¿Estás seguro de que quieres borrar esta noticia?")
            builder.setPositiveButton("Sí, borrar") { _, _ ->
                if (idNoticia != null) {
                    FirebaseFirestore.getInstance().collection("noticias").document(idNoticia)
                        .delete()
                        .addOnSuccessListener {
                            finish()
                        }
                }
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarNoticiaActivity::class.java)
            intent.putExtra("idNoticia", idNoticia)
            intent.putExtra("titulo", titulo)
            intent.putExtra("bajada", bajada)
            intent.putExtra("autor", autor)
            intent.putExtra("cuerpo", cuerpo)
            intent.putExtra("imagenUrl", imagenUrl)
            startActivity(intent)
            finish()
        }
    }
}