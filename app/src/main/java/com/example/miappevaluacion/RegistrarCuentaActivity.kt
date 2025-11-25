package com.example.miappevaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class RegistrarCuentaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cuenta)

        val etNombre = findViewById<EditText>(R.id.etNombreRegistro)
        val etEmail = findViewById<EditText>(R.id.etEmailRegistro)
        val etPass = findViewById<EditText>(R.id.etPasswordRegistro)
        val etPassConfirm = findViewById<EditText>(R.id.etConfirmPasswordRegistro)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val btnVolver = findViewById<Button>(R.id.btnVolverRegistro)

        btnCrearCuenta.setOnClickListener {
            val nombre = etNombre.text.toString()
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            val passConfirm = etPassConfirm.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty() || passConfirm.isEmpty()) {
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
                        val userId = FirebaseAuth.getInstance().currentUser?.uid

                        if (userId != null) {
                            val nuevoUsuario = hashMapOf(
                                "uid" to userId,
                                "nombre" to nombre,
                                "email" to email,
                                "fechaCreacion" to Date(),
                                "rol" to "usuario"
                            )

                            FirebaseFirestore.getInstance().collection("usuarios")
                                .document(userId)
                                .set(nuevoUsuario)
                                .addOnSuccessListener {
                                    mostrarAlerta("Cuenta Creada", "¡Tu cuenta ha sido registrada exitosamente!") {
                                        finish()
                                    }
                                }
                                .addOnFailureListener {
                                    mostrarAlerta("Atención", "Usuario creado, pero hubo un error guardando los datos.")
                                }
                        }
                    } else {
                        mostrarAlerta("Error al registrar", task.exception?.message ?: "Error desconocido")
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