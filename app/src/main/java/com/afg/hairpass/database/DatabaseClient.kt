package com.afg.hairpass.database

import android.content.Context
import androidx.room.Room


class DatabaseClient private constructor(context: Context) {

    var appDatabase: AppDatabase

    companion object {
        private var mInstance: DatabaseClient? = null
        
        fun getInstance(context: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(context)
            }
            return mInstance
        }
    }

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "booking_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}