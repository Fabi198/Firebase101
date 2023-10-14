package com.example.username.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent) //Iniciamos la nueva Activity
            finish() //Finalizamos la Activity actual
        }, DELAYED_TIME) //Como segundo parámetro le pasamos el tiempo de espera que tendrá antes
        // de que se ejecuté el bloque de código dentro del método run()
    }

    companion object {
        private const val DELAYED_TIME: Long = 2000
    }
}