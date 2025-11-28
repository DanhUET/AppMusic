package com.danh.core_database.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danh.core_database.data.dao.SongFavoriteDao
import com.danh.core_database.data.model.SongFavorite

@Database(entities = [SongFavorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun SongFavoriteDao(): SongFavoriteDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "SongFavorite_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }
    }

}