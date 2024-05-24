package com.event.eventmanagement.extras

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.DatePicker
import com.event.eventmanagement.R
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object AppUtils {

    private lateinit var calendar: Calendar

    fun showDatePickerDialog(context: Context, textView: MaterialTextView) {
        calendar = Calendar.getInstance()
        // Initialize DatePickerDialog with current date
        val datePickerDialog = DatePickerDialog(context,
            { view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update calendar with selected date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update TextView with selected date
                updateDateInView(textView)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    @SuppressLint("DefaultLocale")
    fun showTimePicker(context: Context, textView: MaterialTextView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Update TextView with selected time
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                textView.text = formattedTime
            },
            hour,
            minute,
            true // 24-hour format
        )

        timePickerDialog.show()

    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }

    fun formatDate2(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }

    private fun formatTime(context: Context, hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = android.text.format.DateFormat.getTimeFormat(context)
        return dateFormat.format(calendar.time)
    }

    private fun updateDateInView(textView: MaterialTextView) {
        val dateFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        textView.text = sdf.format(calendar.time)
    }

    fun showListSelectionDialog(context: Context, title:String, list: ArrayList<String>, textView: MaterialTextView) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setItems(list.toTypedArray()) { dialogInterface: DialogInterface, i: Int ->
            // Update TextView with selected day
            textView.text = list[i]
            dialogInterface.dismiss() // Dismiss the dialog
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showProgressDialog(context: Context): AlertDialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_progress_dialog, null)

        val builder = AlertDialog.Builder(context, R.style.RoundedCornersDialog)
            .setView(view)
            .setCancelable(false) // Prevent dialog from being dismissed by the back button or outside touch

        val dialog = builder.create()
        dialog.show()
        return dialog
    }

}