package com.afg.hairpass.database.dao

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afg.hairpass.model.ModelDatabase



@Dao
interface DatabaseDao {

    @Query("SELECT * FROM tbl_booking")
    fun getAll(): LiveData<List<ModelDatabase>>

    @Query("SELECT SUM(harga) FROM tbl_booking")
    fun getSaldo(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(modelDatabases: ModelDatabase)

    @Query("DELETE FROM tbl_booking WHERE uid= :uid")
    fun deleteSingleData(uid: Int)

}