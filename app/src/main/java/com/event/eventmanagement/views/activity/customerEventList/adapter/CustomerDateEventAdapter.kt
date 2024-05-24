package com.event.eventmanagement.views.activity.customerEventList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.views.activity.createCustomerEvent.EventDates
import com.event.eventmanagement.views.activity.customerEventList.data.CustomerEventDates
import com.google.android.material.textview.MaterialTextView

class CustomerDateEventAdapter(private val list: ArrayList<CustomerEventDates>) :
    RecyclerView.Adapter<CustomerDateEventAdapter.CustomDateViewHolder>() {

  //  private val list: ArrayList<EventDates> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomDateViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_date_item, parent, false)
        return CustomDateViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CustomDateViewHolder,
        position: Int
    ) {
        val current = list[position]
        holder.remark.text = current.remark
        holder.eventDate.text = "Date : ${current.fromDate}"
        holder.eventTime.text = "Time : ${current.fromTime}-${current.toTime}"
        val dataInFormat = AppUtils.formatDate2(current.fromDate!!).split(" ")

        holder.eventDateCard.text = "${dataInFormat[0]}\n${dataInFormat[1].toUpperCase()}"
    }

//    fun updateList(newList:ArrayList<EventDates>){
//        list.clear()
//        list.addAll(newList)
//        notifyDataSetChanged()
//    }

//    fun addSingle(newItem: EventDates){
//        if (!list.contains(newItem)){
//            list.add(newItem)
//            notifyDataSetChanged()
//        }
//    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class CustomDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val remark :MaterialTextView = itemView.findViewById(R.id.remark)
        val eventDate:MaterialTextView  = itemView.findViewById(R.id.eventDate)
        val eventTime:MaterialTextView = itemView.findViewById(R.id.eventTime)
        val eventDateCard:MaterialTextView = itemView.findViewById(R.id.eventDateCard)

    }

}