package com.example.arkdinostats.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.arkdinostats.R

class SplashActivity : AppCompatActivity() {
    internal val TIME_OUT = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },TIME_OUT.toLong())
    }
}