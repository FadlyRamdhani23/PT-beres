package com.afg.hairpass.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afg.hairpass.database.dao.DatabaseDao
import com.afg.hairpass.model.ModelDatabase



@Database(entities = [ModelDatabase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?
}