package com.example.arkdinostats.model

import android.util.Log
import android.widget.Toast
import com.example.arkdinostats.MyApp
import com.example.arkdinostats.R
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
    var baseTorpidity: Float
) {

    companion object {

        fun createDinos(jsonDinos: List<JsonDino>): List<Dino> {
            var repetead = 0
            val list: MutableList<Dino> = mutableListOf()
            val flashList: MutableList<String> = mutableListOf()
            for (JsonDino in jsonDinos) {
                doLog(JsonDino)
                try {
                    Log.i("Util", "${JsonDino.breeding.incubationTime}")
                } catch (ex: NullPointerException) {
                    if (JsonDino.taming.nonViolent == false && JsonDino.taming.violent == false) {
                        continue
                    }
                }
                var jsonName: String
                if (JsonDino.fullStatsRaw[3] == null || JsonDino.fullStatsRaw[4] == null) {
                    flashList.add(JsonDino.name)
                } else {
                    jsonName = checkVariants(JsonDino)
                    var dinoImage = putImage(JsonDino.name)
                    if (!isRepeated(jsonName, list)) {
                        list.add(
                            Dino(
                                jsonName,
                                image = dinoImage,
                                baseHP = JsonDino.fullStatsRaw[0][0]
                                ,
                                baseStamina = JsonDino.fullStatsRaw[1][0]
                                ,
                                baseTorpidity = JsonDino.fullStatsRaw[2][0],
                                baseOxygen = JsonDino.fullStatsRaw[3][0]
                                ,
                                baseFood = JsonDino.fullStatsRaw[4][0],
                                baseWeight = JsonDino.fullStatsRaw[7][0]
                                ,
                                baseDamage = JsonDino.fullStatsRaw[8][0] * 100,
                                baseSpeed = JsonDino.fullStatsRaw[9][0] * 100
                            )
                        )
                    } else {
                        repetead += 1
                    }
                }
            }
            Toast.makeText(
                MyApp.instance,
                "Dinos cargados. Normal: ${list.size}. Flasheros: ${flashList.size}. Repetidos: $repetead",
                Toast.LENGTH_SHORT
            ).show()
            list.sortBy { dino -> dino.name }
            return list
        }

        private fun checkVariants(jsonDino: JsonDino): String {
            try {
                if (!jsonDino.name.contains(jsonDino.variants[0])) {
                    return jsonDino.name + " " + jsonDino.variants[0]
                } else {
                    return jsonDino.name
                }
            } catch (ex: NullPointerException) {
                return jsonDino.name
            }
        }

        private fun isRepeated(
            jsonName: String,
            list: MutableList<Dino>
        ): Boolean {
            for (Dino in list) {
                if (Dino.name.equals(jsonName)) {
                    return true
                }
            }
            return false
        }

        private fun doLog(jsonDino: JsonDino) {
            try {
                Log.i("Util", "${jsonDino.breeding.incubationTime}")
            } catch (ex: NullPointerException) {
                if (jsonDino.taming.nonViolent == false && jsonDino.taming.violent == false) {
                }
                Log.i("Check", jsonDino.name)
            }
        }

        private fun putImage(name: String): Int {
            if (name.contains("Parasaur")) {
                return R.drawable.ic_parasaur
            } else if (name.contains("Pteranodon")) {
                return R.drawable.ic_pteranodon
            } else if (name.contains("Ankylosaurus")) {
                return R.drawable.ic_ankylo
            } else if (name.contains("Mosasaurus")) {
                return R.drawable.ic_mosasaurio
            } else if (name.contains("Carnotaurus")) {
                return R.drawable.ic_carno
            } else if (name.contains("Spino")) {
                return R.drawable.ic_spino
            } else if (name.contains("Stegosaurus")) {
                return R.drawable.ic_stego
            } else if (name.contains("Rex")) {
                return R.drawable.ic_trex
            } else if (name.contains("Triceratops")) {
                return R.drawable.ic_triceratops
            } else {
                return R.drawable.ic_launcher_foreground
            }
        }
    }
}