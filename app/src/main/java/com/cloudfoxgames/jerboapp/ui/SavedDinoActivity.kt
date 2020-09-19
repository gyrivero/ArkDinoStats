package com.cloudfoxgames.jerboapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.db.entity.DinoEntity
import com.cloudfoxgames.jerboapp.viewmodel.SavedDinosViewModel
import kotlinx.android.synthetic.main.fragment_calculator.*

class SavedDinoActivity : AppCompatActivity() {
    private lateinit var viewModel : SavedDinosViewModel

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("new",false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_dino)

        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayout,
            SavedDinosFragment()
        ).commit()

        viewModel = ViewModelProvider(this).get(SavedDinosViewModel::class.java)

        var new = true

        if (savedInstanceState != null) {
            new = savedInstanceState.getBoolean("new")
        }

        if (new) {
            val bundle = intent.getBundleExtra("dino")
            if (bundle != null) {
                viewModel.insert(DinoEntity(bundle.getString("name")!!, bundle.getString("type")!!,bundle.getInt("image"),
                    bundle.getInt("lvl"),null,bundle.getInt("hp"),bundle.getInt("stamina"),bundle.getInt("oxygen"),
                    bundle.getInt("food"),bundle.getInt("weight"),bundle.getInt("damage"),bundle.getInt("wasted"),
                    bundle.getInt("speed")))
            }
        }
    }
}