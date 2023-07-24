package com.example.electricbillcaluclator.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electricbillcaluclator.R
import com.example.electricbillcaluclator.roomdb.Record


private const val TAG = "DataRecordAdapter"

class DataRecordAdapter internal constructor(context: Context) :

    RecyclerView.Adapter<DataRecordAdapter.DataRecordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemsList = emptyList<Record>().toMutableList()

    private val onClickListener: View.OnClickListener

    init {

        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Record

            Log.d(TAG, "Setting onClickListener for item ${item.customerSeviceNo}")

        }
    }

    /* This is an `inner class` that associates associates the items in the ViewHolder
       layout with variables that will be used inside OnBindViewHolder.
    */
    inner class DataRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_customerId: TextView = itemView.findViewById(R.id.tv_customer_id)
        val tv_totalUnits: TextView = itemView.findViewById(R.id.tv_total_units)
        val tv_consumption: TextView = itemView.findViewById(R.id.tv_consumption)
    }

    /* Basically, inflates the ViewHolder layout and returns a ViewHolder object
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRecordViewHolder {
        val itemView = inflater.inflate(R.layout.record_list, parent, false)
        return DataRecordViewHolder(itemView)
    }

    /* This is where the ViewHolder gets populated with data from the Item.
       Position inside the RecyclerView is also available.
     */
    override fun onBindViewHolder(holder: DataRecordViewHolder, position: Int) {
        val current = itemsList[position]

        // Needed: will be referenced in the View.OnClickListener above
        holder.itemView.tag = current

        with(holder) {
            // Set UI values
            tv_customerId.text = current.customerSeviceNo.toString()
            tv_totalUnits.text = current.meterReading.toString()
            tv_consumption.text = current.consumption.toString()

            // Set handlers
            itemView.setOnClickListener(onClickListener)
        }
    }

    internal fun setItems(items: List<Record>) {
        this.itemsList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemsList.size
}