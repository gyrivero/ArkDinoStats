package com.cloudfoxgames.jerboapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.model.Dino
import com.cloudfoxgames.jerboapp.ui.DinoFragment
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

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