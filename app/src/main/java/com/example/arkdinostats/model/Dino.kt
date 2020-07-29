package com.example.arkdinostats.model

import com.example.arkdinostats.MyApp
import com.example.arkdinostats.R
import com.example.arkdinostats.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Dino(val name: String, var image: Int) {


    companion object {

        fun jsonparse(): List<Json4Kotlin_Base> {
            val gson = Gson()
            val fileJson = Utils.getJsonDataFromAsset(MyApp.instance,"values.json")
            val listJson = object : TypeToken<List<Json4Kotlin_Base>>(){}.type
            var listDinos : List<Json4Kotlin_Base> = gson.fromJson(fileJson,listJson)
            return listDinos
        }

        fun allDinos() : List<Dino> {
            val list : MutableList<Dino> = mutableListOf()
            list.add(Dino("Parasaur", R.drawable.ic_parasaur))
            list.add(Dino("Pteranodon", R.drawable.ic_pteranodon))
            list.add(Dino("Ankylosaur",R.drawable.ic_ankylo))
            list.add(Dino("Mosasaurus",R.drawable.ic_mosasaurio))
            list.add(Dino("Carnotaurus",R.drawable.ic_carno))
            list.add(Dino("Spinosaurus",R.drawable.ic_spino))
            list.add(Dino("Stegosaurus",R.drawable.ic_stego))
            list.add(Dino("T-Rex",R.drawable.ic_trex))
            list.add(Dino("Triceratops",R.drawable.ic_triceratops))
            list.sortBy { dino -> dino.name  }
            return list
        }
    }
}