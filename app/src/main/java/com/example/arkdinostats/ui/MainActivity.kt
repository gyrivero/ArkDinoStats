package com.example.arkdinostats.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.arkdinostats.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer,
            DinoFragment()
        ).commit()
    }
}