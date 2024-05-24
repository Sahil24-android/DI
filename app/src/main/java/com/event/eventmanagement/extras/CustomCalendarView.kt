package com.event.eventmanagement.extras

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.TextView
import com.event.eventmanagement.R
import java.util.*

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val calendar: Calendar = Calendar.getInstance()
    private val eventsMap: MutableMap<Int, List<String>> = mutableMapOf()



    private val gridView: GridView
    private val adapter: CalendarAdapter

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_calendar_view, this, true)
        gridView = findViewById(R.id.calendarGridView)
        adapter = CalendarAdapter(context)
        gridView.adapter = adapter
        updateCalendar()
    }

    fun setEvents(events: Map<Int, List<String>>) {
        eventsMap.clear()
        eventsMap.putAll(events)
        adapter.notifyDataSetChanged()
    }

    private fun updateCalendar() {
        // No need to update the calendar here if we're using GridView
    }

    inner class CalendarAdapter(context: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        override fun getItem(position: Int): Any {
            return position + 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_calendar_day, parent, false)

            val dayTextView = view.findViewById<TextView>(R.id.dayTextView)
            val eventIndicator = view.findViewById<View>(R.id.eventIndicator)

            val dayOfMonth = position + 1
            dayTextView.text = dayOfMonth.toString()

            if (eventsMap.containsKey(dayOfMonth)) {
                eventIndicator.visibility = View.VISIBLE
            } else {
                eventIndicator.visibility = View.GONE
            }

            return view
        }
    }
}