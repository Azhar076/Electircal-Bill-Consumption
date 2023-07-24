package com.example.electricbillcaluclator.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataRecordDao {
    @Query("SELECT * from record_table")
    fun getall(): LiveData<List<Record>>

    @Insert
     fun insert(item: Record)

    @Query("SELECT * FROM record_table WHERE customerSeviceNo = :id")
    fun get(id: String): LiveData<Record>

    @Update
     fun update(vararg items: Record)

    @Delete
     fun delete(vararg items: Record)
}