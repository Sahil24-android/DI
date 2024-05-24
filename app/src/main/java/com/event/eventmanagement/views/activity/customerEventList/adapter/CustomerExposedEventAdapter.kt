package com.event.eventmanagement.views.activity.customerEventList.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.EventListItemExposeToMeBinding
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class CustomerExposedEventAdapter(private val context: Context) :
    RecyclerView.Adapter<CustomerExposedEventAdapter.CustomerViewHolder>() {
    private val list = ArrayList<EventData>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerExposedEventAdapter.CustomerViewHolder {
        val view = EventListItemExposeToMeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CustomerExposedEventAdapter.CustomerViewHolder,
        position: Int
    ) {
        val current = list[position]
        val customerData = current.customerdata[0]
        holder.binding.customerName.text = customerData.customerName
        holder.binding.mobileNumber.text = customerData.mobNo
        holder.binding.eventAddress.text = current.eventAddress
//        holder.advanceAmount.text = "₹${current.advanceAmount}"


        val dateAdapter = CustomerDateEventAdapter(current.eventDates)
        holder.binding.eventDateRecycler.adapter = dateAdapter
        holder.binding.eventDateRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.eventDateRecycler.setHasFixedSize(true)




        holder.binding.exposingAmount.text = "₹${current.transferEvent?.exposingAmount}"


        holder.binding.vendorName.text = current.exposedFrom!!.ownerName
        holder.binding.companyName.text = current.exposedFrom!!.companyName
        holder.binding.contactDetails.text = current.exposedFrom!!.mobNo
        holder.binding.vendorAddress.text = current.exposedFrom!!.address

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: ArrayList<EventData>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CustomerViewHolder(val binding: EventListItemExposeToMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}