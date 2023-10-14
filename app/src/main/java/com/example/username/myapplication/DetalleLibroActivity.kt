package com.example.username.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.username.myapplication.databinding.ActivityDetalleLibroBinding
import java.sql.SQLException

class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleLibroBinding
    private var libro: Libro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        libro = getLibroById(intent.getIntExtra("libro", 0))
        initTextViews()
    }

    private fun getLibroById(id: Int): Libro {
        return try {
            LibroManager.getInstance(this).getLibro(id)
        } catch (e: SQLException) {
            e.printStackTrace()
            Libro()
        }
    }

    private fun initTextViews() {
        libro?.let {
            binding.txtLibro.text = it.nombre
            binding.txtAutor.text = it.autor
        }
    }
}