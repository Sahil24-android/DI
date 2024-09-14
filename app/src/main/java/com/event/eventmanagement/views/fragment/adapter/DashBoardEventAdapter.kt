package com.event.eventmanagement.views.fragment.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.views.activity.customerEventList.adapter.CustomerDateEventAdapter
import com.event.eventmanagement.views.activity.customerEventList.adapter.PaymentsAdapter
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class DashBoardEventAdapter(private val context: Context) :
    RecyclerView.Adapter<DashBoardEventAdapter.CustomerViewHolder>() {
    private val list = ArrayList<EventData>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashBoardEventAdapter.CustomerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.event_list_item, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashBoardEventAdapter.CustomerViewHolder, position: Int) {
        val current = list[position]
        val customerData = current.customerdata[0]
        holder.customerName.text = customerData.customerName
        holder.customerMobile.text = customerData.mobNo
        holder.eventAddress.text = current.eventAddress
        holder.finalAmountAfterDiscount.text = "₹${current.finalAmount}"
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
            holder.remainingAmount.text = spannableString
        } else {
            holder.remainingAmount.text = "₹${current.remainingAmount}"
        }
        //holder.remainingAmount.text = "₹${current.remainingAmount}"
        holder.eventPackageName.text = current.eventPkg[0].packageName

        val dateAdapter = CustomerDateEventAdapter(current.eventDates)
        holder.dateRecyclerView.adapter = dateAdapter
        holder.dateRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.dateRecyclerView.setHasFixedSize(true)

        holder.view.setOnClickListener {
            if (holder.paymentsLayout.visibility == View.VISIBLE) {
                holder.paymentsLayout.visibility = View.GONE
//                holder.eventItem.text = "Show Pays" // Update button text if needed
            } else {
                holder.paymentsLayout.visibility = View.VISIBLE
//                holder.eventItem.text = "Hide Pays" // Update button text if needed
            }
        }
        if (current.remainingAmount.toString() == "0") {
            holder.eventItem.visibility = View.GONE
        } else {
            holder.eventItem.visibility = View.VISIBLE
        }

        holder.advanceAmount.text = "₹${current.advanceAmount}"

        val adapter = PaymentsAdapter(context)
        holder.paymentsRecycler.adapter = adapter
        holder.paymentsRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        adapter.updateList(current.eventPayment)
        holder.paymentsRecycler.setHasFixedSize(true)

        if (current.isTransfer==1){
            holder.exposeEvent.visibility = View.GONE
            holder.exposedEventText.visibility = View.VISIBLE
        }else{
            holder.exposeEvent.visibility = View.VISIBLE
            holder.exposedEventText.visibility = View.GONE
        }

        holder.showMore.setOnClickListener {
            if (holder.hideAndShow.visibility == View.VISIBLE) {
                holder.hideAndShow.visibility = View.GONE
            } else {
                holder.hideAndShow.visibility = View.VISIBLE
            }
        }

        holder.view.visibility = View.GONE
        holder.finalAmountAfterDiscount.visibility = View.GONE
        holder.showMore.visibility = View.GONE
        holder.exposeEvent.visibility = View.GONE
        holder.exposedEventText.visibility = View.GONE
        holder.paymentsLayout.visibility = View.GONE
        holder.hideAndShow.visibility = View.GONE
        holder.paymentsRecycler.visibility = View.GONE
        holder.advanceAmount.visibility = View.GONE
        holder.remainingAmount.visibility = View.GONE
        holder.eventItem.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: ArrayList<EventData>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = list.size
            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition].id == newList[newItemPosition].id // Assuming EventData has an 'id' field
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition] == newList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: MaterialTextView = itemView.findViewById(R.id.customerName)
        val customerMobile: MaterialTextView = itemView.findViewById(R.id.mobileNumber)
        val eventAddress: MaterialTextView = itemView.findViewById(R.id.eventAddress)
        val dateRecyclerView: RecyclerView = itemView.findViewById(R.id.eventDateRecycler)
        val finalAmountAfterDiscount: MaterialTextView = itemView.findViewById(R.id.finalAmount)
        val eventPackageName: MaterialTextView = itemView.findViewById(R.id.eventPackageName)
        val showMore:ImageButton = itemView.findViewById(R.id.showMore)
        //        val showPayments:LinearLayout = itemView.findViewById(R.id.paymentsLayout)
        val view: ShapeableImageView = itemView.findViewById(R.id.view)
        val remainingAmount: MaterialTextView = itemView.findViewById(R.id.remainingAmount)
        val eventItem: MaterialButton = itemView.findViewById(R.id.addPayments)
        val paymentsLayout: LinearLayout = itemView.findViewById(R.id.paymentsLayout)
        val paymentsRecycler: RecyclerView = itemView.findViewById(R.id.paymentRecycler)
        val advanceAmount: MaterialTextView = itemView.findViewById(R.id.advanceAmount)
        val exposeEvent: ImageButton = itemView.findViewById(R.id.exposeEvent)
        val exposedEventText:MaterialTextView = itemView.findViewById(R.id.exposedEventText)
        val hideAndShow:LinearLayout = itemView.findViewById(R.id.hideThisLayoutForExposed)

        init {
//            eventItem.setOnClickListener {
//                listener.actionItemClick(adapterPosition, list[adapterPosition])
//            }
//            exposeEvent.setOnClickListener {
//                listener.exposeEvent(adapterPosition, list[adapterPosition])
//            }
        }
    }

    interface OnClickListener {
        fun actionItemClick(position: Int, item: EventData)
        fun exposeEvent(position: Int, item: EventData)
    }

}