package com.event.eventmanagement.views.activity.exposed

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityExposingEventBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.customerEventList.adapter.CustomerDateEventAdapter
import com.event.eventmanagement.views.activity.customerEventList.adapter.CustomerEventAdapter
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.event.eventmanagement.views.activity.exposed.data.ExposedBody
import com.event.eventmanagement.views.auth.datasource.Vendor

class ExposingEventActivity : AppCompatActivity(), CustomerEventAdapter.OnClickListener {
    private lateinit var binding: ActivityExposingEventBinding
    private lateinit var userViewModel: UserViewModel
    private val vendorList: ArrayList<Vendor> = ArrayList()
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityExposingEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val eventData = intent.getParcelableExtra<EventData>("event")
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        var sendToVendor = 0
        userViewModel.getAllVendor()

        binding.lessAmount.text =
            getString(R.string.enter_amount_less_than, eventData?.finalAmount.toString())

        userViewModel.vendorList.observe(this) { response ->
            vendorList.clear()
            if (response.vendor.isNotEmpty()) {
                vendorList.addAll(response.vendor)
            } else {
                Toast.makeText(this, "No Vendor Found", Toast.LENGTH_SHORT).show()
            }
        }


        binding.mobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 10) {
                    for (item in vendorList) {
                        if (item.mobNo == s.toString()) {
                            Toast.makeText(
                                this@ExposingEventActivity,
                                "Vendor Found",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.vendorDetailLayout.visibility = View.VISIBLE

                            binding.vendorName.text = item.ownerName
                            binding.vendorAddress.text = item.address
                            binding.contactDetails.text = "${item.mobNo}"
                            binding.companyName.text = item.companyName

                            sendToVendor = item.id!!
                        } else {
                            Toast.makeText(
                                this@ExposingEventActivity,
                                "Vendor Not Found",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.vendorDetailLayout.visibility = View.GONE
                            binding.vendorName.text = null
                            binding.vendorAddress.text = null
                            binding.contactDetails.text = null
                            binding.companyName.text = null
                        }
                    }
                } else {
                    binding.vendorDetailLayout.visibility = View.GONE
                    Toast.makeText(
                        this@ExposingEventActivity,
                        "Incorrect Number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })

        binding.actualPrice.text = "\u20b9 ${eventData?.finalAmount}"

        binding.customerName.text = eventData!!.customerdata[0].customerName
        binding.customerMobileNumber.text = eventData.customerdata[0].mobNo
        binding.eventAddress.text = eventData.eventAddress

        binding.eventDateRecycler.layoutManager = LinearLayoutManager(this)
        val adapter = CustomerDateEventAdapter(eventData.eventDates)
        binding.eventDateRecycler.adapter = adapter
        


        binding.exposeAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    if (s.toString().toInt() > binding.actualPrice.text.toString()
                            .replace("\u20b9 ", "").toInt()
                    ) {
                        binding.lessAmount.visibility = View.VISIBLE
                    } else {
                        binding.lessAmount.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@ExposingEventActivity, "Enter Amount", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })


        binding.exposeEventButton.setOnClickListener {
            val exposedBody = ExposedBody(
                eventData.id,
                preferenceManager.getVendorId(),
                sendToVendor,
                eventData.finalAmount,
                binding.exposeAmount.text.toString().toInt()
            )

            userViewModel.transferEvent(exposedBody)
        }


        userViewModel.eventTransferResponse.observe(this){response ->
            if (response.Transfer != null){
                finish()
            }else{
                Toast.makeText(this,"Enable To Procees",Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun actionItemClick(position: Int, item: EventData) {

    }

    override fun exposeEvent(position: Int, item: EventData) {

    }
}