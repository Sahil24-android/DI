package com.event.eventmanagement.views.activity.vendorExpense

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityAddVendorExpenseBinding
import com.event.eventmanagement.databinding.ActivityExposingEventBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.event.eventmanagement.views.activity.vendorExpense.data.VendorExpenseBody
import com.event.eventmanagement.views.auth.datasource.Vendor

class AddVendorExpense : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var binding: ActivityAddVendorExpenseBinding
    private val vendorList: ArrayList<Vendor> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityAddVendorExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        preferenceManager = PreferenceManager(this)


        binding.back.setOnClickListener {
            finish()
        }
        userViewModel.getAllVendor()

        userViewModel.vendorList.observe(this) { response ->
            vendorList.clear()
            if (response.vendor.isNotEmpty()) {
                vendorList.addAll(response.vendor)
            } else {
                Toast.makeText(this, "No Vendor Found", Toast.LENGTH_SHORT).show()
            }
        }

        var sendToVendor = 0


        binding.mobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 10) {
                    val listFilter = vendorList.filter { it.mobNo == s.toString() }
                    if (listFilter.isNotEmpty()) {
                        binding.vendorDetailLayout.visibility = View.VISIBLE
                        binding.vendorName.text = listFilter[0].ownerName
                        binding.vendorAddress.text = listFilter[0].address
                        binding.contactDetails.text = "${listFilter[0].mobNo}"
                        binding.companyName.text = listFilter[0].companyName
                        sendToVendor = listFilter[0].id!!
                        userViewModel.getEventByVendorId(sendToVendor.toString())
                    } else {
                        binding.vendorDetailLayout.visibility = View.GONE
                        binding.vendorName.text = null
                        binding.vendorAddress.text = null
                        binding.contactDetails.text = null
                        binding.companyName.text = null
                        sendToVendor = 0
                        Toast.makeText(
                            this@AddVendorExpense, "Vendor Not Found", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@AddVendorExpense, "Vendor Not Found", Toast.LENGTH_SHORT
                    ).show()
                    // binding.vendorDetailLayout.visibility = View.GONE
                    binding.vendorName.text = null
                    binding.vendorAddress.text = null
                    binding.contactDetails.text = null
                    binding.companyName.text = null
                }
            }


        })


        val hasMap: HashMap<String, Int> = HashMap()
        val list = ArrayList<String>()
        val eventData = ArrayList<EventData>()
        userViewModel.getVendorEvent.observe(this) { result ->
            if (result != null) {
                list.clear()
                eventData.addAll(result.data)
                for (item in result.data) {
                    val builder = StringBuilder()
                    builder.append("Customer Name: ${item.customerdata[0].customerName}")
                        .append("\n").append("Event Address: ${item.eventAddress}").append("\n")
                        .append("Exposing Amount: ${item.transferEvent!!.exposingAmount}")
                    list.add(builder.toString())
                    hasMap[builder.toString()] = item.id!!
                }
                val adapter = ArrayAdapter<String>(
                    this@AddVendorExpense, android.R.layout.simple_dropdown_item_1line, list
                )
                binding.selectEvent.setAdapter(adapter)
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
//        binding.selectEvent.setOnItemClickListener { parent, view, position, id ->
//            hasMap[parent.getItemAtPosition(position).toString()] = id.toInt()
//        }

        var eventId: Int? = null
        var remainingAmount: Int? = null

        binding.selectEvent.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            eventId = hasMap[selectedItem]
            val amount = extractAmount(selectedItem)
            //  remainingAmount = amount?.toInt()

            if (eventData.isNotEmpty()) {
                val data = eventData.filter { it.id == eventId }
                if (data[0].expensePayment.isNotEmpty()) {
                    remainingAmount =
                        data[0].expensePayment[data[0].expensePayment.lastIndex].remainingAmount
                    binding.remainingAmountText.text = "Remaining Amount: \u20B9 ${remainingAmount}"
                }else{
                    remainingAmount = amount?.toInt()
                    binding.remainingAmountText.text = "Remaining Amount: \u20B9 ${remainingAmount}"


                }
            } else {
                remainingAmount = amount?.toInt()
                binding.remainingAmountText.text = "Remaining Amount: \u20B9 ${remainingAmount}"

            }
            //   Log.d("data", data[0].expensePayment.toString())

        }

        binding.expenseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    binding.remainingAmount.text =
                        "Remaining Amount: \u20B9 ${remainingAmount?.minus(s.toString().toInt())}"
                } else {
                    binding.remainingAmount.text = "Remaining Amount: \u20B9 ${remainingAmount}"
                }
            }

        })

        userViewModel.addVendorExpense.observe(this) {
            if (it.expense != null) {
                Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveExpense.setOnClickListener {
            if (eventId != null && sendToVendor != 0) {

                val amount = binding.expenseAmount.text.toString()
                val description = binding.expenseDescription.text.toString()
                if (amount.isNotEmpty()) {
                    if (remainingAmount!! >= amount.toInt()) {
                        val vendorExpenseBody = VendorExpenseBody(
                            vendorId = preferenceManager.getVendorId(),
                            expenseName = description,
                            description = description,
                            expenseToVendor = sendToVendor.toString(),
                            eventManageId = eventId,
                            amount = amount.toInt(),
//                        remainingAmount = 500
                            remainingAmount = remainingAmount!!.minus(
                                amount.toInt()
                            )
                        )

                        userViewModel.addVendorExpense(vendorExpenseBody)
                    } else {
                        Toast.makeText(this, "Amount Exceeded", Toast.LENGTH_SHORT).show()
                    }


                } else {
                    Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please Select Vendor and Event", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun extractAmount(input: String): String? {
        val regex = Regex("Exposing Amount: (\\d+(\\.\\d+)?)")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)
    }
}