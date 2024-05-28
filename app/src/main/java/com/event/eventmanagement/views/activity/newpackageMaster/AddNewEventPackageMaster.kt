package com.event.eventmanagement.views.activity.newpackageMaster

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityAddNewEventPackageMasterBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.fragment.datasource.PackageBody
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class AddNewEventPackageMaster : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewEventPackageMasterBinding
    private val checkedEventItem: ArrayList<String> = ArrayList()
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private var vendorId:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewEventPackageMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        preferenceManager  = PreferenceManager(this)

        vendorId = preferenceManager.getVendorId().toString()

        userViewModel.getEvents(vendorId!!)
        val map: HashMap<String, Int> = HashMap()
        val listEvent = ArrayList<String>()
        userViewModel.getAllEventsResponse.observe(this) { result ->
            listEvent.clear()
            if (result != null) {
                val response = result
                val list = response.data
                for (item in list) {
                    listEvent.add(item.eventName!!)
                    map[item.eventName!!] = item.id!!
                }
            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        var selectedItems: ArrayList<String> = ArrayList()
        binding.selectEventType.setOnClickListener {
            selectedItems = showCustomDialog(listEvent)
        }

        binding.savePackage.setOnClickListener {
            val packageName = binding.packageName.text.toString()
            val packageDescription = binding.packageDescription.text.toString()
            val packageAmount = binding.packageAmount.text.toString()
            val builder = StringBuilder()
            for (item in checkedEventItem) {
                if (map.containsKey(item)) {
                    val eventId = map[item]
                    builder.append("$eventId#")
                }
            }
            val packageBody = PackageBody(
                packageName = packageName,
                description = packageDescription,
                amount = packageAmount.toInt(),
                eventId = builder.substring(0, builder.length - 1).toString(),
                vendorId = vendorId!!.toInt()

            )
            userViewModel.createPackage(packageBody)
            Log.d(
                "save",
                "$checkedEventItem $packageName $packageDescription $packageAmount $packageBody"
            )
        }

        userViewModel.packageResponse.observe(this) { result ->
            if (result != null) {
                val response = result
                if (response.msg!!.contains("Event Added Successfully")) {
                    Toast.makeText(this, response.msg, Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }



        binding.back.setOnClickListener {
            finish()
        }


    }


    private fun showCustomDialog(items: ArrayList<String>): ArrayList<String> {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.event_list_dialog, null)
        val listView = dialogView.findViewById<ListView>(R.id.listView)
        val save = dialogView.findViewById<MaterialButton>(R.id.save)
        val cancel = dialogView.findViewById<MaterialButton>(R.id.cancel)

        val selectedItems = ArrayList<String>()

        val adapter = ArrayAdapter(this, R.layout.event_checked_item, R.id.textView, items)
        listView.adapter = adapter

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .setTitle("Event List")
            .create()

        checkedEventItem.clear()
        binding.chipGroup.removeAllViews()

        save.setOnClickListener {
            // Iterate through the ListView's adapter to get checked items
            for (i in 0 until listView.adapter.count) {
                // Get the view for each item in the ListView
                val itemView = listView.getChildAt(i)

                // Find the checkbox within the item view
                val checkBox = itemView?.findViewById<CheckBox>(R.id.checkBox)

                // If the checkbox is checked, add the corresponding item to the list of selected items
                if (checkBox?.isChecked == true) {
                    selectedItems.add(items[i])
                }
            }

            // Display the selected items
            checkedEventItem.addAll(selectedItems)
            for (item in selectedItems) {
                val chip = Chip(this)
                chip.text = item
                chip.isCloseIconVisible = true
                chip.setOnCloseIconClickListener {
                    binding.chipGroup.removeView(chip)
                    checkedEventItem.remove(item)
                }
                binding.chipGroup.addView(chip)
            }
            val message = "Selected items: ${selectedItems.joinToString()}"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        // Return the selected items
        return selectedItems
    }

//    private fun showCustomDialog(items:ArrayList<String>
//    ):ArrayList<String>
//    {
//        val dialogView =
//            LayoutInflater.from(this).inflate(R.layout.event_list_dialog, null)
//        val listView = dialogView.findViewById<ListView>(R.id.listView)
//        val save = dialogView.findViewById<MaterialButton>(R.id.save)
//        val cancel = dialogView.findViewById<MaterialButton>(R.id.cancel)
//
//
//        val adapter = ArrayAdapter(this, R.layout.event_checked_item,R.id.textView, items)
//        listView.adapter = adapter
//
//        val dialog = AlertDialog.Builder(this,R.style.CustomAlertDialog)
//            .setView(dialogView)
//            .setTitle("Event List")
//            .create()
//
//        checkedEventItem.clear()
//        binding.chipGroup.removeAllViews()
//
//        save.setOnClickListener {
//            val selectedItems = mutableListOf<String>()
//
//            // Iterate through the ListView's adapter to get checked items
//            for (i in 0 until listView.adapter.count) {
//                // Get the view for each item in the ListView
//                val itemView = listView.getChildAt(i)
//
//                // Find the checkbox within the item view
//                val checkBox = itemView?.findViewById<CheckBox>(R.id.checkBox)
//
//                // If the checkbox is checked, add the corresponding item to the list of selected items
//                if (checkBox?.isChecked == true) {
//                    selectedItems.add(items[i])
//                }
//            }
//
//            // Display the selected items
//            checkedEventItem.addAll(selectedItems)
//            for(item in selectedItems){
//                val chip = Chip(this)
//                chip.text = item
//                chip.isCloseIconVisible = true
//                chip.setOnCloseIconClickListener {
//                    binding.chipGroup.removeView(chip)
//                    checkedEventItem.remove(item)
//                }
//                binding.chipGroup.addView(chip)
//            }
//            val message = "Selected items: ${selectedItems.joinToString()}"
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//        cancel.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }

}