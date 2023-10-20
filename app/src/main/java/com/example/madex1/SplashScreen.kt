package com.example.madex1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val i = Intent(this, MainActivity::class.java)
        Handler().postDelayed(Runnable {
            startActivity(i)
            finish()
        }, 2000)
    }
}