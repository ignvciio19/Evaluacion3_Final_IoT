package com.example.miappevaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditarNoticiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_noticia)

        val etTitulo = findViewById<EditText>(R.id.etTituloEditar)
        val etBajada = findViewById<EditText>(R.id.etBajadaEditar)
        val etAutor = findViewById<EditText>(R.id.etAutorEditar)
        val etCuerpo = findViewById<EditText>(R.id.etCuerpoEditar)
        val etUrlImagen = findViewById<EditText>(R.id.etUrlImagenEditar)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambios)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarEditar)

        val idNoticia = intent.getStringExtra("idNoticia")
        etTitulo.setText(intent.getStringExtra("titulo"))
        etBajada.setText(intent.getStringExtra("bajada"))
        etAutor.setText(intent.getStringExtra("autor"))
        etCuerpo.setText(intent.getStringExtra("cuerpo"))
        etUrlImagen.setText(intent.getStringExtra("imagenUrl"))

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val bajada = etBajada.text.toString()
            val autor = etAutor.text.toString()
            val cuerpo = etCuerpo.text.toString()
            val url = etUrlImagen.text.toString()

            if (titulo.isEmpty() || bajada.isEmpty() || autor.isEmpty() || cuerpo.isEmpty()) {
                mostrarAlerta("Error", "Completa todos los campos")
                return@setOnClickListener
            }

            val noticiaActualizada = hashMapOf<String, Any>(
                "titulo" to titulo,
                "bajada" to bajada,
                "autor" to autor,
                "cuerpo" to cuerpo,
                "imagenUrl" to url
            )

            if (idNoticia != null) {
                FirebaseFirestore.getInstance().collection("noticias").document(idNoticia)
                    .update(noticiaActualizada)
                    .addOnSuccessListener {
                        mostrarAlerta("Éxito", "Noticia actualizada correctamente") {
                            finish()
                        }
                    }
                    .addOnFailureListener {
                        mostrarAlerta("Error", "No se pudo actualizar")
                    }
            }
        }

        btnCancelar.setOnClickListener {
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