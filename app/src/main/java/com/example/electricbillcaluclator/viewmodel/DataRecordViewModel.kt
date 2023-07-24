package com.example.electricbillcaluclator.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.electricbillcaluclator.DataRecordRepository
import com.example.electricbillcaluclator.roomdb.AppRoomDatabase
import com.example.electricbillcaluclator.roomdb.Record
import kotlinx.coroutines.launch

class DataRecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRecordRepository
    val allItems: LiveData<List<Record>>

    init {
        Log.d(TAG, "Inside ViewModel init")
        val dao = AppRoomDatabase.getDatabase(application).datarecordDao()
        repository = DataRecordRepository(dao)
        allItems = repository.allItems
    }

    fun insert(item: Record) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: Record) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: Record) = viewModelScope.launch {
        repository.delete(item)
    }

    fun get(id: String) = repository.get(id)
}