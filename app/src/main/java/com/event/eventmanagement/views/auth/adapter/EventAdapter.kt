package com.event.eventmanagement.views.auth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.views.fragment.datasource.Event
import com.google.android.material.textview.MaterialTextView

class EventAdapter(private val context: Context) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val eventList = ArrayList<Event>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventName.text = event.eventName
        holder.eventDescription.text = event.description
    }

    override fun getItemCount(): Int {
        return eventList.size
    }


    fun updateList(newList: List<Event>) {
        eventList.clear()
        eventList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: MaterialTextView = itemView.findViewById(R.id.eventName)
        val eventDescription: MaterialTextView = itemView.findViewById(R.id.eventDescription)

    }


}