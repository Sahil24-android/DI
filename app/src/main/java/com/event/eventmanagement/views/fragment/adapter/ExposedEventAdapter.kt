package com.event.eventmanagement.views.fragment.adapter

import android.content.Context
import android.graphics.Typeface
import android.media.metrics.Event
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.EventItemBinding
import com.event.eventmanagement.databinding.EventListItemBinding
import com.event.eventmanagement.databinding.EventListItemExposedByMeBinding
import com.event.eventmanagement.views.activity.customerEventList.adapter.CustomerDateEventAdapter
import com.event.eventmanagement.views.activity.customerEventList.adapter.PaymentsAdapter
import com.event.eventmanagement.views.activity.customerEventList.data.EventData

class ExposedEventAdapter(private val context: Context):RecyclerView.Adapter<ExposedEventAdapter.ExposedEventViewHolder>() {

    private val list:ArrayList<EventData> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExposedEventAdapter.ExposedEventViewHolder {
        val view = EventListItemExposedByMeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExposedEventViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExposedEventAdapter.ExposedEventViewHolder,
        position: Int
    ) {
       val current = list[position]

        val customerData = current.customerdata[0]
        holder.binding.customerName.text = customerData.customerName
        holder.binding.mobileNumber.text = customerData.mobNo
        holder.binding.eventAddress.text = current.eventAddress
        holder.binding.finalAmount.text = "₹${current.finalAmount}"
//        holder.advanceAmount.text = "₹${current.advanceAmount}"
        if (current.remainingAmount == 0) {
            val spannableString = SpannableString("All Payment Done")
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(context.resources.getColor(R.color.green_pays)),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.binding.remainingAmount.text = spannableString
        } else {
            holder.binding.remainingAmount.text = "₹${current.remainingAmount}"
        }
        //holder.remainingAmount.text = "₹${current.remainingAmount}"
        holder.binding.eventPackageName.text = current.eventPkg[0].packageName

        val dateAdapter = CustomerDateEventAdapter(current.eventDates)
        holder.binding.eventDateRecycler.adapter = dateAdapter
        holder.binding.eventDateRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.eventDateRecycler.setHasFixedSize(true)

        holder.binding.view.setOnClickListener {
            if (holder.binding.paymentsLayout.visibility == View.VISIBLE) {
                holder.binding.paymentsLayout.visibility = View.GONE
//                holder.eventItem.text = "Show Pays" // Update button text if needed
            } else {
                holder.binding.paymentsLayout.visibility = View.VISIBLE
//                holder.eventItem.text = "Hide Pays" // Update button text if needed
            }
        }
        if (current.remainingAmount.toString() == "0") {
            holder.binding.eventItem.visibility = View.GONE
        } else {
            holder.binding.eventItem.visibility = View.VISIBLE
        }
        holder.binding.advanceAmount.text = "₹${current.advanceAmount}"

        val adapter = ExposedPaymentAdapter(context)
        holder.binding.paymentRecycler.adapter = adapter
        holder.binding.paymentRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        adapter.updateList(current.expensePayment)
        holder.binding.paymentRecycler.setHasFixedSize(true)


        holder.binding.exposeEvent.visibility = View.GONE

        holder.binding.vendorName.text = current.exposedTo!!.ownerName
        holder.binding.companyName.text = current.exposedTo!!.companyName
        holder.binding.contactDetails.text = current.exposedTo!!.mobNo
        holder.binding.vendorAddress.text = current.exposedTo!!.address
        holder.binding.exposingAmount.text = "₹${current.transferEvent!!.exposingAmount}"

        holder.binding.hideFromExposing.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList:ArrayList<EventData>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    inner class ExposedEventViewHolder(val binding: EventListItemExposedByMeBinding):RecyclerView.ViewHolder(binding.root){

    }

}