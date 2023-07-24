package com.example.electricbillcaluclator

import androidx.lifecycle.LiveData
import com.example.electricbillcaluclator.roomdb.DataRecordDao
import com.example.electricbillcaluclator.roomdb.Record

class DataRecordRepository(private val datarecordDao: DataRecordDao) {

    val allItems: LiveData<List<Record>> = datarecordDao.getall()

    fun get(id: String):LiveData<Record> {
        return datarecordDao.get(id)
    }

    suspend fun update(item: Record) {
        datarecordDao.update(item)
    }

    suspend fun insert(item: Record) {
        datarecordDao.insert(item)
    }

    suspend fun delete(item: Record) {
        datarecordDao.delete(item)
    }
}
