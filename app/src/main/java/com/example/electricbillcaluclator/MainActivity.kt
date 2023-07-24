package com.example.electricbillcaluclator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electricbillcaluclator.adapter.DataRecordAdapter
import com.example.electricbillcaluclator.databinding.ActivityMainBinding
import com.example.electricbillcaluclator.roomdb.Record
import com.example.electricbillcaluclator.viewmodel.DataRecordViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var datarecordViewModel: DataRecordViewModel
    var consumption: Int? =null
    var readingDifference: Int? =null
    var meterReadingUnits:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)
        val adapter = DataRecordAdapter(this)
        binding.rvRecord.adapter = adapter
        binding.rvRecord.layoutManager = LinearLayoutManager(this)
        datarecordViewModel.allItems.observe(this, Observer { items ->
            items?.let {
                adapter.setItems(it)
            }
        })

        binding.btnSubmit.setOnClickListener {
          val customerServiceNo = binding.etCustomerServiceNo.text.toString()
          val meterReading = binding.etMeterReading.text.toString()

          if(!isAlphaNumeric(customerServiceNo) || customerServiceNo.length < 10) {
              Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
              return@setOnClickListener
          }
          val currentReading = meterReading.toIntOrNull()
          if (currentReading == null || currentReading <= 0) {
              Toast.makeText(this, "Invalid current reading", Toast.LENGTH_SHORT).show()
          } else {

              datarecordViewModel.get(customerServiceNo).observeOnce(this, Observer { data ->

                  if (data != null) {
                      if(currentReading > data.meterReading){
                           readingDifference = currentReading!! - data.meterReading
                          consumption = getConsumption(readingDifference!!)
                          datarecordViewModel.insert(Record(readingDifference!!,customerServiceNo,consumption!!))

                      }else{
                          Toast.makeText(this, "Current reading is less then Previous", Toast.LENGTH_SHORT).show()
                      }

                  } else {
                      consumption = getConsumption(currentReading!!)
                      datarecordViewModel.insert(Record(meterReading.toInt(),customerServiceNo,consumption!!))

                  }
                  binding.etMeterReading.setText("")
                  binding.etCustomerServiceNo.setText("")

              })

          }
      }



    }

    fun isAlphaNumeric(input: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9]+$")
        return regex.matches(input)
    }

     fun getConsumption(meterReadingUnits: Int): Int{
         consumption = 0
        var remaining:Int? = null
        val dataMap = HashMap<String, Any>()
         if(meterReadingUnits!! >= 100){
             dataMap["0"] =  100 * 5
             remaining  = meterReadingUnits - 100
             if(remaining >= 400){
                 dataMap["1"] = remaining * 8
                 remaining = remaining - 400
                 if(remaining > 0){
                     dataMap["2"] = remaining * 10
                 }

             }else{
                 dataMap["1"] = remaining * 8
             }

         }else{
             dataMap["0"] =  meterReadingUnits * 5
         }
         var sum = 0
         for (entry in dataMap.values) {
             if (entry is Int) {
                 sum += entry
             }
         }

        return sum!!
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(value: T) {
                observer.onChanged(value)
                removeObserver(this)
            }
        })
    }
}