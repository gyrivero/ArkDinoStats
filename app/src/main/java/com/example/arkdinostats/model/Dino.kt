package com.example.arkdinostats.model

import android.util.Log
import android.widget.Toast
import com.example.arkdinostats.MyApp
import com.example.arkdinostats.R
import java.io.Serializable
import java.lang.NullPointerException

class Dino(
    val name: String,
    var image: Int,
    var baseHP: Float,
    var baseStamina: Float,
    var baseOxygen: Float,
    var baseFood: Float,
    var baseWeight: Float,
    var baseDamage: Float,
    var baseSpeed: Float,
    var baseTorpidity: Float,
    var iwHP: Float,
    var iwStamina: Float,
    var iwOxygen: Float,
    var iwFood: Float,
    var iwWeight: Float,
    var iwDamage: Float,
    var iwSpeed: Float,
    var iwTorpidity: Float,
    var taHP: Float,
    var taStamina: Float,
    var taOxygen: Float,
    var taFood: Float,
    var taWeight: Float,
    var taDamage: Float,
    var taSpeed: Float,
    var taTorpidity: Float,
    var tmHP: Float,
    var tmStamina: Float,
    var tmOxygen: Float,
    var tmFood: Float,
    var tmWeight: Float,
    var tmDamage: Float,
    var tmSpeed: Float,
    var tmTorpidity: Float,
    var tbhm: Float,
    var aberrantTek : String
) : Serializable