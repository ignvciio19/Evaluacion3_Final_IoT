package com.example.miappevaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class AgregarNoticiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_noticia)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etBajada = findViewById<EditText>(R.id.etBajada)
        val etAutor = findViewById<EditText>(R.id.etAutor)
        val etCuerpo = findViewById<EditText>(R.id.etCuerpo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarNoticia)
        val btnVolver = findViewById<Button>(R.id.btnVolverNoticia) // Botón Nuevo

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val bajada = etBajada.text.toString()
            val autor = etAutor.text.toString()
            val cuerpo = etCuerpo.text.toString()

            if (titulo.isEmpty() || bajada.isEmpty() || autor.isEmpty() || cuerpo.isEmpty()) {
                mostrarAlerta("Error", "Completa todos los campos")
                return@setOnClickListener
            }

            val noticia = hashMapOf(
                "titulo" to titulo,
                "bajada" to bajada,
                "autor" to autor,
                "cuerpo" to cuerpo,
                "fecha" to Date()
            )

            FirebaseFirestore.getInstance().collection("noticias")
                .add(noticia)
                .addOnSuccessListener {
                    mostrarAlerta("Éxito", "Noticia publicada correctamente") {
                        finish()
                    }
                }
                .addOnFailureListener {
                    mostrarAlerta("Error", "No se pudo guardar la noticia")
                }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String, alAceptar: (() -> Unit)? = null) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar") { _, _ ->
            alAceptar?.invoke()
        }
        builder.show()
    }
}