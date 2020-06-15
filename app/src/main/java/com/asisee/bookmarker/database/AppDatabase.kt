package com.asisee.bookmarker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val bookmarkDao: BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "bookmark_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}