package com.example.arkdinostats.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.arkdinostats.db.dao.DinoDao
import com.example.arkdinostats.db.entity.DinoEntity

@Database(entities = arrayOf(DinoEntity::class),version = 1,exportSchema = false)
public abstract class DinoRoomDatabase : RoomDatabase() {
    abstract fun dinoDao(): DinoDao

    companion object {
        @Volatile
        private var INSTANCE: DinoRoomDatabase? = null

        fun getDatabase(context: Context) : DinoRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DinoRoomDatabase::class.java,
                    "dino_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}