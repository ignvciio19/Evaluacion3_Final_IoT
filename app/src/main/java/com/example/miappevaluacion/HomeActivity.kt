package com.example.miappevaluacion

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var rvNoticias: RecyclerView
    private lateinit var adapter: NoticiasAdapter
    private val listaNoticias = mutableListOf<Noticia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fabAgregar = findViewById<FloatingActionButton>(R.id.fabAgregar)
        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)
        rvNoticias = findViewById(R.id.rvNoticias)

        rvNoticias.layoutManager = LinearLayoutManager(this)
        adapter = NoticiasAdapter(listaNoticias) { noticia ->
            val intent = Intent(this, VerNoticiaActivity::class.java)
            intent.putExtra("idNoticia", noticia.id)
            intent.putExtra("titulo", noticia.titulo)
            intent.putExtra("bajada", noticia.bajada)
            intent.putExtra("cuerpo", noticia.cuerpo)
            intent.putExtra("autor", noticia.autor)
            intent.putExtra("imagenUrl", noticia.imagenUrl)
            startActivity(intent)
        }
        rvNoticias.adapter = adapter

        fabAgregar.setOnClickListener {
            val intent = Intent(this, AgregarNoticiaActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cerrar Sesión")
            builder.setMessage("¿Estás seguro de que quieres salir?")
            builder.setPositiveButton("Sí, salir") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarNoticias()
    }

    private fun cargarNoticias() {
        FirebaseFirestore.getInstance().collection("noticias")
            .get()
            .addOnSuccessListener { result ->
                listaNoticias.clear()
                for (document in result) {
                    val noticia = document.toObject(Noticia::class.java)
                    noticia.id = document.id
                    listaNoticias.add(noticia)
                }
                adapter.notifyDataSetChanged()
            }
    }
}