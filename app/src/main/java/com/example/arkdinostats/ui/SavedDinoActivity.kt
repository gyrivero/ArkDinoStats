package com.example.arkdinostats.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.arkdinostats.R
import com.example.arkdinostats.db.entity.DinoEntity
import com.example.arkdinostats.viewmodel.SavedDinosViewModel
import kotlinx.android.synthetic.main.fragment_calculator.*

class SavedDinoActivity : AppCompatActivity() {
    private lateinit var viewModel : SavedDinosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_dino)

        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayout,
            SavedDinosFragment()
        ).commit()

        viewModel = ViewModelProvider(this).get(SavedDinosViewModel::class.java)
        val bundle = intent.getBundleExtra("dino")
        if (bundle != null) {
            viewModel.insert(DinoEntity(bundle.getString("name")!!,bundle.getInt("image"),bundle.getInt("lvl"),
                null,bundle.getInt("hp"),bundle.getInt("stamina"),bundle.getInt("oxygen"),bundle.getInt("food"),
            bundle.getInt("weight"),bundle.getInt("damage"),bundle.getInt("wasted"),bundle.getInt("speed")))
        }

        /*bundle.putInt("image",actualDino.image)
        bundle.putInt("hp", pointsHP)
        bundle.putInt("stamina", pointsStamina)
        bundle.putInt("oxygen", pointsOxygen)
        bundle.putInt("food", pointsFood)
        bundle.putInt("weight", pointsWeight)
        bundle.putInt("damage", pointsDamage)
        bundle.putInt("speed", pointsSpeed)
        bundle.putString("name",actualDino.name)
        bundle.putString("lvl",lvlET.text.toString())*/



    }
}