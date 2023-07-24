package com.example.electricbillcaluclator.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "record_table")
data class Record(@PrimaryKey(autoGenerate = true)
                  val meterReading: Int,
                  val customerSeviceNo: String,
                   val consumption: Int)