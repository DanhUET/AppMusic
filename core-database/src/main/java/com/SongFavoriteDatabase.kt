package com

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dao.song.SongDao
import com.entity.song.SongFavorite

@Database(entities = [SongFavorite::class], version = 1, exportSchema = false)
abstract class SongFavoriteDatabase : RoomDatabase() {
    abstract fun getSongFavoriteDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: SongFavoriteDatabase? = null

        fun getDatabase(context: Context): SongFavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    SongFavoriteDatabase::class.java,
                    "Song_favorite_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}