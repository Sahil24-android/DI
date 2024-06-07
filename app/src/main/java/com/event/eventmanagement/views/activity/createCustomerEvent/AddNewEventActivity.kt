package com.event.eventmanagement.views.activity.createCustomerEvent

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityAddNewEventBinding
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.extras.CustomProgressDialog
import com.event.eventmanagement.model.LocationViewModel
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.createCustomerEvent.adapter.CustomDateEventAdapter
import com.event.eventmanagement.views.activity.invoice.InvoiceActivity
import com.event.eventmanagement.views.fragment.datasource.EventPackageResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewEventBinding
    private lateinit var customDateEventAdapter: CustomDateEventAdapter
    private  val userViewModel: UserViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private var vendorId:String? = null
    private val customDateEventList: ArrayList<EventDates> = ArrayList()
    private val packageList: ArrayList<EventPackageResponse> = ArrayList()
    private val dialog by lazy { CustomProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)
        vendorId = preferenceManager.getVendorId().toString()
        binding.customDatesRadio.isChecked = true
        binding.customDateLayout.visibility = View.VISIBLE

//        binding.customDatesRadio.isChecked = true
//        binding.customDateLayout.visibility = View.VISIBLE

        //  binding.singleDateRadio.setBackgroundColor(resources.getColor(R.color.blueColorDark))

        userViewModel.isLoading.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }

        }

        locationViewModel.isLoading.observe(this){
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        userViewModel.getEventPackages(vendorId!!)
        userViewModel.getEventPackages.observe(this) { result ->
            packageList.clear()
            if (result != null) {
                val response = result
                if (response.data.isNotEmpty()) {
                    packageList.addAll(response.data)
                    val stringArray: ArrayList<String> = ArrayList()
                    for (i in packageList.indices) {
                        stringArray.add(
                            "Package Name:${packageList[i].packageName}\n" +
                                    "Package Price:${packageList[i].amount}\n" +
                                    "Package Includes:${
                                        packageList[i].newKey.map { it.eventName }
                                            .joinToString(",")
                                    }"
                        )
                    }
                    val adapter = ArrayAdapter<String>(
                        this@AddNewEventActivity,
                        R.layout.dropdown_item, stringArray
                    )
                    binding.selectServiceType.setAdapter(adapter)
                }
            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }


        binding.dateRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.singleDateRadio -> {
                    binding.singleDateLayout.visibility = View.VISIBLE
                    binding.multipleDateLayout.visibility = View.GONE
                    binding.customDateLayout.visibility = View.GONE
                }

                R.id.multipleDatesRadio -> {
                    binding.multipleDateLayout.visibility = View.VISIBLE
                    binding.singleDateLayout.visibility = View.GONE
                    binding.customDateLayout.visibility = View.GONE

                }

                R.id.customDatesRadio -> {
                    binding.customDateLayout.visibility = View.VISIBLE
                    binding.singleDateLayout.visibility = View.GONE
                    binding.multipleDateLayout.visibility = View.GONE
                }
            }
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.dateOfBirth.setOnClickListener {
            AppUtils.showDatePickerDialog(this, binding.dateOfBirth)
        }

        binding.anniversaryDate.setOnClickListener {
            AppUtils.showDatePickerDialog(this, binding.anniversaryDate)
        }

        binding.addCustomDate.setOnClickListener {
            showCustomDialog()
        }

        binding.selectPinCode.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().length == 6) {
                        locationViewModel.getLocationsData(s.toString())
                    } else {
                        binding.state.text = null
                        binding.city.text = null
                        binding.country.text = null
                    }
                }

            })

        locationViewModel.locationData.observe(this) { result ->
            if (result != null) {
                val getFirstData = result.get(0)
                if (getFirstData.PostOffice.isNotEmpty()) {
                    try {
                        val data = getFirstData.PostOffice.get(0)
                        binding.state.text = data.State
                        binding.city.text = data.Block
                        binding.country.text = data.Country
                    }catch (e:Exception){
                        binding.state.text = null
                        binding.city.text = null
                        binding.country.text = null
                        binding.selectPinCode.text = null
                        Toast.makeText(this,"Invalid PinCode",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid PinCode", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.state.text = null
                binding.city.text = null
                binding.country.text = null
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }



        customDateEventAdapter = CustomDateEventAdapter()
        binding.customDateRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.customDateRecycleView.adapter = customDateEventAdapter
        customDateEventAdapter.updateList(customDateEventList)

        ItemTouchHelper(
            object : ItemTouchHelper.Callback() {
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val swipeFlags = ItemTouchHelper.LEFT
                    return makeMovementFlags(0, swipeFlags)
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val deletedItem = customDateEventList[position]

                    // Remove the item from your dataset
                    customDateEventList.removeAt(position)
                    customDateEventAdapter.updateList(customDateEventList)

                    // Show an undo option
                    Snackbar.make(
                        binding.customDateRecycleView,
                        "Item deleted",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("UNDO") {
                            // Undo deletion
                            customDateEventList.add(position, deletedItem)
                            customDateEventAdapter.updateList(customDateEventList)

                        }
                        .addCallback(object : Snackbar.Callback() {
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                if (event != DISMISS_EVENT_ACTION) {
                                    // Item not undone, perform necessary actions (e.g., delete from database)
                                }
                            }
                        })
                        .show()
                }

            }).attachToRecyclerView(binding.customDateRecycleView)
        var packageId = 0
        binding.selectServiceType.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = packageList[position]
            packageId = selectedItem.id!!
            binding.packageAmount.text = "Rs. ${selectedItem.amount}"
        }

        binding.discountAmount.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (binding.discountAmount.text!!.isNotEmpty()) {
                        val packageAmount =
                            binding.packageAmount.text.toString().replace("Rs. ", "").toInt()
                        val discountAmount = binding.discountAmount.text.toString().toInt()
                        val finalAmount = packageAmount - discountAmount
                        binding.finalAmount.text = "Rs. $finalAmount"
                    } else {
                        Toast.makeText(
                            this@AddNewEventActivity,
                            "Please enter discount amount",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })

        binding.advanceAmount.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (binding.advanceAmount.text!!.isNotEmpty()) {
                        val finalAmount =
                            binding.finalAmount.text.toString().replace("Rs. ", "").toInt()
                        val advanceAmount = binding.advanceAmount.text.toString().toInt()
                        val remainingAmount = finalAmount - advanceAmount
                        binding.remaining.text = "Rs. ${remainingAmount}"
                    }
                }

            })

        var bodyRequest: EventBodyRequest? = null

        binding.saveEvent.setOnClickListener {
            if (!fieldValidation()) {
                return@setOnClickListener
            } else {
                bodyRequest = EventBodyRequest(
                    vendorId = preferenceManager.getVendorId().toString(),
                    customerName = binding.customerName.text.toString(),
                    dob = binding.dateOfBirth.text.toString(),
                    anniversaryDate = binding.anniversaryDate.text.toString() ?: " ",
                    mobNo = binding.mobileNumber.text.toString(),
                    altMobNo = if (binding.alternateMobileNumber.text.toString().isEmpty()) {
                        binding.mobileNumber.text.toString()
                    } else {
                        binding.alternateMobileNumber.text.toString()
                    },
                    address = binding.address.text.toString(),
                    countryId = binding.country.text.toString(),
                    stateId = binding.state.text.toString(),
                    cityId = binding.city.text.toString(),
                    pincode = binding.selectPinCode.text.toString(),
                    description = binding.eventDescription.text.toString(),
                    eventDates = customDateEventList,
                    eventAddress = binding.eventVenueAddress.text.toString(),
                    amount = binding.packageAmount.text.toString().replace("Rs. ", "").toInt(),
                    eventPkgId = packageId,
                    discount = binding.discountAmount.text.toString().replace("Rs. ", "").toInt(),
                    finalAmount = binding.finalAmount.text.toString().replace("Rs. ", "").toInt(),
                    advanceAmount = binding.advanceAmount.text.toString().replace("Rs. ", "")
                        .toInt(),
                    remainingAmount = binding.remaining.text.toString().replace("Rs. ", "").toInt(),
                )
                userViewModel.addNewEvent(bodyRequest!!)
            }
        }

        userViewModel.newEventResponse.observe(this) { result ->
            if (result != null) {
                Toast.makeText(this, "Event Added Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InvoiceActivity::class.java)
                intent.putExtra("firstReceipt", true)
                intent.putExtra("invoiceData", bodyRequest)
                intent.putExtra("PackageName", binding.selectServiceType.text.toString())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun showCustomDialog(
    ) {
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.customize_event_date_dialog, null)

        val date = dialogView.findViewById<MaterialTextView>(R.id.eventDate)
        val fromTime = dialogView.findViewById<MaterialTextView>(R.id.eventFromTime)
        val toTime = dialogView.findViewById<MaterialTextView>(R.id.eventToTime)
        val remark = dialogView.findViewById<TextInputEditText>(R.id.eventRemark)
        val save = dialogView.findViewById<MaterialButton>(R.id.save)
        val cancel = dialogView.findViewById<MaterialButton>(R.id.cancel)

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .setTitle("Custom Date")
            .create()


        date.setOnClickListener {
            AppUtils.showDatePickerDialog(this, date)
        }
        fromTime.setOnClickListener {
            AppUtils.showTimePicker(this, fromTime)
        }
        toTime.setOnClickListener {
            AppUtils.showTimePicker(this, toTime)
        }
        save.setOnClickListener {
            val customEventDates = EventDates(
                date.text.toString(),
                date.text.toString(),
                fromTime.text.toString(),
                toTime.text.toString(),
                remark.text.toString()
            )
            customDateEventList.add(customEventDates)
            customDateEventAdapter.updateList(customDateEventList)
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun fieldValidation(): Boolean {
        if (binding.customerName.text.toString().isEmpty()) {
            binding.customerName.error = "Please enter customer name"
            return false
        }
        if (binding.dateOfBirth.text.toString().isEmpty()) {
            binding.dateOfBirth.error = "Please enter date of birth"
            return false
        }
        if (binding.mobileNumber.text.toString().isEmpty()) {
            binding.mobileNumber.error = "Please enter mobile number"
            return false
        }
        if (binding.address.text.toString().isEmpty()) {
            binding.address.error = "Please enter address"
            return false
        }
        if (binding.eventVenueAddress.text.toString().isEmpty()) {
            binding.eventVenueAddress.error = "Please enter event venue address"
            return false
        }
        if (binding.selectPinCode.text.toString().isEmpty()) {
            binding.selectPinCode.error = "Please enter pin code"
            return false
        }
        if (binding.packageAmount.text.toString().isEmpty()) {
            binding.packageAmount.error = "Please enter package amount"
            return false
        }
        if (binding.finalAmount.text.toString().isEmpty()) {
            binding.finalAmount.error = "Please enter final amount"
            return false
        }
        if (binding.advanceAmount.text.toString().isEmpty()) {
            binding.advanceAmount.error = "Please enter advance amount"
            return false
        }
        if (binding.remaining.text.toString().isEmpty()) {
            binding.remaining.error = "Please enter remaining amount"
            return false
        }
        return true
    }
}