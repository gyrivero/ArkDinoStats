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
    var tbhm: Float

) {

    companion object {

        fun createDinos(jsonDinos: List<JsonDino>): List<Dino> {
            var repetead = 0
            val list: MutableList<Dino> = mutableListOf()
            val flashList: MutableList<String> = mutableListOf()
            for (JsonDino in jsonDinos) {
                Log.i("Checkf","Name: ${JsonDino.name}. TBHM: ${JsonDino.tamedBaseHealthMultiplier}")
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
                            Dino(jsonName, dinoImage, JsonDino.fullStatsRaw[0][0], JsonDino.fullStatsRaw[1][0],
                                JsonDino.fullStatsRaw[3][0], JsonDino.fullStatsRaw[4][0],
                                JsonDino.fullStatsRaw[7][0], JsonDino.fullStatsRaw[8][0] * 100,
                                JsonDino.fullStatsRaw[9][0] * 100, JsonDino.fullStatsRaw[2][0],
                                JsonDino.fullStatsRaw[0][1],JsonDino.fullStatsRaw[1][1],JsonDino.fullStatsRaw[3][1],
                                JsonDino.fullStatsRaw[4][1],JsonDino.fullStatsRaw[7][1],JsonDino.fullStatsRaw[8][1],
                                JsonDino.fullStatsRaw[9][1],JsonDino.fullStatsRaw[2][1],JsonDino.fullStatsRaw[0][3],
                                JsonDino.fullStatsRaw[1][3],JsonDino.fullStatsRaw[3][3],JsonDino.fullStatsRaw[4][3],
                                JsonDino.fullStatsRaw[7][3],JsonDino.fullStatsRaw[8][3],JsonDino.fullStatsRaw[9][3],
                                JsonDino.fullStatsRaw[2][3],JsonDino.fullStatsRaw[0][4],JsonDino.fullStatsRaw[1][4],
                                JsonDino.fullStatsRaw[3][4],JsonDino.fullStatsRaw[4][4],JsonDino.fullStatsRaw[7][4],
                                JsonDino.fullStatsRaw[8][4],JsonDino.fullStatsRaw[9][4],JsonDino.fullStatsRaw[2][4],
                                JsonDino.tamedBaseHealthMultiplier
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
                Log.i("Util", "${jsonDino.displayedStats}")
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