package com.example.username.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.username.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val user = "USUARIO"
    private val pref: SharedPreferences? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrearUsuario.setOnClickListener { mostrarInProgress() }

        binding.btnIniciarSesion.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val password = binding.etContraseA.text.toString()
            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Completar datos", Toast.LENGTH_SHORT).show()
            } else {
                guardarSharedPref(usuario)
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                intent.putExtra("USUARIO", usuario)
                intent.putExtra("PASSWORD", password)
                startActivity(intent)
                finish()
            }
        }

        cargarSharedPref()

    }

    private fun mostrarInProgress() {
        Snackbar.make(binding.container, "En progreso", Snackbar.LENGTH_LONG).show()
    }

    private fun guardarSharedPref(usuario: String) {
        val editor = pref?.edit()
        editor?.putString(user, usuario)
        editor?.apply()
    }

    private fun cargarSharedPref() {
        val usuario = pref?.getString(user, "")
        binding.etUsuario.setText(usuario)
    }
}