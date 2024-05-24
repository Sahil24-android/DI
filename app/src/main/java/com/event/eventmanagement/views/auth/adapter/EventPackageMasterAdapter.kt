package com.event.eventmanagement.views.auth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.views.fragment.datasource.EventPackageResponse
import com.google.android.material.textview.MaterialTextView

class EventPackageMasterAdapter() :
    RecyclerView.Adapter<EventPackageMasterAdapter.EventPackageMasterViewHolder>() {

        private val list: ArrayList<EventPackageResponse>  = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventPackageMasterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_event_package, parent, false)
        return EventPackageMasterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EventPackageMasterViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.packageName.text = item.packageName
        holder.packageAmount.text = "\u20B9 ${item.amount}"
        holder.packageDescription.text = item.description
        val includes = item.newKey
        val builder = StringBuilder()
        for (itemData in includes){
            builder.append(itemData.eventName).append("\n")
        }
        holder.packageIncludes.text = builder.toString()

    }

    fun updateList(newList: ArrayList<EventPackageResponse>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class EventPackageMasterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val packageName = itemView.findViewById<MaterialTextView>(R.id.packageName)
        val packageAmount = itemView.findViewById<MaterialTextView>(R.id.packageAmount)
        val packageDescription = itemView.findViewById<MaterialTextView>(R.id.packageDescription)
        val packageIncludes = itemView.findViewById<MaterialTextView>(R.id.packageIncludes)
    }

}