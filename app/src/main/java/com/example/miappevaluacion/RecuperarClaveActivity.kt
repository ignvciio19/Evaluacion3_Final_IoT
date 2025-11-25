package com.example.miappevaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperarClaveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_clave)

        val etEmail = findViewById<EditText>(R.id.etEmailRecuperar)
        val btnEnviar = findViewById<Button>(R.id.btnEnviarRecuperacion)
        val btnVolver = findViewById<Button>(R.id.btnVolverRecuperar)

        btnEnviar.setOnClickListener {
            val email = etEmail.text.toString()

            if (email.isEmpty()) {
                mostrarAlerta("Error", "Por favor ingresa tu correo.")
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mostrarAlerta("Correo enviado", "Se ha enviado un enlace para restablecer tu clave.") {
                            finish()
                        }
                    } else {
                        mostrarAlerta("Error", "No se pudo enviar el correo. Verifica que el usuario exista.")
                    }
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