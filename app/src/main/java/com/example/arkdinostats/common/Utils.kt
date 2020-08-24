package com.example.arkdinostats.common

import android.content.Context
import com.example.arkdinostats.model.JsonDino
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object Utils {
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.applicationContext.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun jsonParse(fileName : String): List<JsonDino> {
        val gson = Gson()
        val fileJson = getJsonDataFromAsset(MyApp.instance, fileName)
        val listJson = object : TypeToken<List<JsonDino>>() {}.type
        var listDinos: List<JsonDino> = gson.fromJson(fileJson, listJson)
        return listDinos
    }
}