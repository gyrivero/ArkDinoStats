package com.example.arkdinostats.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dinos")
class DinoEntity(
    val name: String,
    val image: Int,
    @PrimaryKey(autoGenerate = true) val id: Int,
    val hpPoints: Int,
    val staminaPoints: Int,
    val oxygenPoints: Int,
    val foodPoints: Int,
    val weightPoints: Int,
    val damagePoints: Int,
    val speedPoints: Int
) {

}