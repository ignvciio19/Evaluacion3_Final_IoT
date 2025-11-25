package com.example.miappevaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistrarCuentaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cuenta)

        val etEmail = findViewById<EditText>(R.id.etEmailRegistro)
        val etPass = findViewById<EditText>(R.id.etPasswordRegistro)
        val etPassConfirm = findViewById<EditText>(R.id.etConfirmPasswordRegistro)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val btnVolver = findViewById<Button>(R.id.btnVolverRegistro) // Botón nuevo

        btnCrearCuenta.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            val passConfirm = etPassConfirm.text.toString()

            if (email.isEmpty() || pass.isEmpty() || passConfirm.isEmpty()) {
                mostrarAlerta("Error", "Por favor completa todos los campos.")
                return@setOnClickListener
            }

            if (pass != passConfirm) {
                mostrarAlerta("Error", "Las contraseñas no coinciden.")
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Cuenta Creada")
                        builder.setMessage("¡Tu cuenta ha sido registrada exitosamente!")
                        builder.setPositiveButton("Aceptar") { _, _ ->
                            finish()
                        }
                        builder.show()
                    } else {
                        mostrarAlerta("Error al registrar", task.exception?.message ?: "Error desconocido")
                    }
                }
        }


        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        builder.show()
    }
}