package com.cloudfoxgames.jerboapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cloudfoxgames.jerboapp.R
import com.google.android.gms.ads.MobileAds
import com.google.gson.JsonObject
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer,
            DinoFragment()
        ).commit()
    }
}