package com.example.username.myapplication

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.username.myapplication.databinding.ActivityHomeBinding
import java.sql.SQLException
import java.util.Calendar

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: LibrosAdapter
    private val airplaneStateReceiver: AirplaneStateReceiver = AirplaneStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saludarUsuario()
        setupToolbar()
        configurarNavigationView()
        setupAdapter()
        setupUI()

        val prefs = getPreferences(MODE_PRIVATE)
        prefs.getString("Nombre", "")
        prefs.getInt("Edad", 0)

        initializeSyncService()

        createSyncAlarm()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.homeToolbar)
        supportActionBar!!.title = "Mis libros"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun configurarNavigationView() {
        val drawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, binding.homeToolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutMe -> irAAboutMe()
                R.id.menuCerrarSesion -> cerrarSesion()
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    private fun irAAboutMe() {
        val intent = Intent(this, AboutMeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun cerrarSesion() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToAgregarLibro() {
        val intent = Intent(this@HomeActivity, AgregarLibroActivity::class.java)
        startActivity(intent)
    }

    private fun setupUI() {
        binding.fabAgregarLibro.setOnClickListener { goToAgregarLibro() }
    }

    private fun setupAdapter() {
        adapter = LibrosAdapter(getLibros()) { libro, txtNombre, txtAutor ->
            goToDetalleLibro(libro, txtNombre, txtAutor)
        }
        binding.rvLibros.adapter = adapter
    }

    private fun goToDetalleLibro(libro: Libro, txtNombre: TextView, txtAutor: TextView) {
        val intent = Intent(this, DetalleLibroActivity::class.java)
        intent.putExtra("libro", libro.id)
        val p1 = Pair.create(txtNombre as View, getString(R.string.nombre_libro))
        val p2 = Pair.create(txtAutor as View, getString(R.string.nombre_autor))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            p1, p2
        )
        startActivity(intent, options.toBundle())
    }

    private fun initializeSyncService() {
        val intent = Intent(this, SyncService::class.java)
        startService(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter.setNuevosLibros(getLibros())
        adapter.notifyDataSetChanged()
        registerReceiver(airplaneStateReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    private fun getLibros(): List<Libro> {
        return try {
            LibroManager.getInstance(this).getLibros()
        } catch (e: SQLException) {
            e.printStackTrace()
            ArrayList()
        }
    }

    private fun saludarUsuario() {
        val bundle = intent.extras
        if (bundle != null) {
            val usuario = bundle.getString("USUARIO")
            Toast.makeText(this@HomeActivity, "Bienvenido $usuario", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, SyncService::class.java))
        unregisterReceiver(airplaneStateReceiver)
        super.onDestroy()
    }

    private fun createSyncAlarm() {
        val intent = Intent(this, SyncDataReceiver::class.java)
        val alarmExists =
            PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE) != null
        if (!alarmExists) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar[Calendar.HOUR_OF_DAY] = 8
            calendar[Calendar.MINUTE] = 0
            alarmManager.setInexactRepeating(
                AlarmManager.RTC,
                calendar.timeInMillis,
                86400000,
                pendingIntent
            )
        }
    }
}