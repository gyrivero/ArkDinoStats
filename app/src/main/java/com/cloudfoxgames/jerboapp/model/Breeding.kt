package com.example.arkdinostats.model

import com.google.gson.annotations.SerializedName


data class Breeding (

	@SerializedName("gestationTime") val gestationTime : Float,
	@SerializedName("incubationTime") val incubationTime : Double,
	@SerializedName("eggTempMin") val eggTempMin : Float,
	@SerializedName("eggTempMax") val eggTempMax : Float,
	@SerializedName("maturationTime") val maturationTime : Double,
	@SerializedName("matingCooldownMin") val matingCooldownMin : Float,
	@SerializedName("matingCooldownMax") val matingCooldownMax : Float
)