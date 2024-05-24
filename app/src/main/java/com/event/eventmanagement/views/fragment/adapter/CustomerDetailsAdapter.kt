package com.event.eventmanagement.views.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.views.fragment.datasource.CustomerDetails
import com.google.android.material.textview.MaterialTextView

class CustomerDetailsAdapter(private val listener :OnCustomerClickListener) :
    RecyclerView.Adapter<CustomerDetailsAdapter.CustomerDetailsViewHolder>() {
    private val list: ArrayList<CustomerDetails> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerDetailsAdapter.CustomerDetailsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.customer_item, parent, false)
        return CustomerDetailsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CustomerDetailsAdapter.CustomerDetailsViewHolder,
        position: Int
    ) {
        val current = list[position]
        holder.customerName.text = current.customerName
        holder.contactNumber.text = "${current.mobNo}\n${current.altMobNo}"
        holder.address.text = current.address
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<CustomerDetails>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    inner class CustomerDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val customerName: MaterialTextView = itemView.findViewById(R.id.customerName)
        val contactNumber: MaterialTextView = itemView.findViewById(R.id.contactDetails)
        val address: MaterialTextView = itemView.findViewById(R.id.customerAddress)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition,list[adapterPosition])
            }
        }

    }
    interface OnCustomerClickListener{
        fun onItemClick(position: Int,item:CustomerDetails)
    }

}