package com.example.miappevaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerNoticiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_noticia)

        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val tvBajada = findViewById<TextView>(R.id.tvBajadaDetalle)
        val tvAutor = findViewById<TextView>(R.id.tvAutorDetalle)
        val tvCuerpo = findViewById<TextView>(R.id.tvCuerpoDetalle)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val titulo = intent.getStringExtra("titulo")
        val bajada = intent.getStringExtra("bajada")
        val cuerpo = intent.getStringExtra("cuerpo")
        val autor = intent.getStringExtra("autor")

        tvTitulo.text = titulo
        tvBajada.text = bajada
        tvAutor.text = "Por: $autor"
        tvCuerpo.text = cuerpo

        btnVolver.setOnClickListener {
            finish()
        }
    }
}