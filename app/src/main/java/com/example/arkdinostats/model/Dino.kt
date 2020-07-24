package com.example.arkdinostats.model

import com.example.arkdinostats.R

class Dino(val name: String, val image: Int) {


    companion object {

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
            return list
        }
    }
}