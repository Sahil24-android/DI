package com.event.eventmanagement.views.activity.customerEventList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityEventBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.addeventPayments.AddPaymentActivity
import com.event.eventmanagement.views.activity.customerEventList.adapter.CustomerEventAdapter
import com.event.eventmanagement.views.activity.customerEventList.adapter.CustomerExposedEventAdapter
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.event.eventmanagement.views.activity.exposed.ExposingEventActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView

class EventActivity : AppCompatActivity(), CustomerEventAdapter.OnClickListener {
    private lateinit var binding: ActivityEventBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: CustomerEventAdapter
    private lateinit var exposedEventAdapter: CustomerExposedEventAdapter
    private lateinit var preferenceManager: PreferenceManager
    private var currentPosition = 0
    private var date: String? = null
    private var exposedListData: ArrayList<EventData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        adapter = CustomerEventAdapter(this, this)
        preferenceManager = PreferenceManager(this)
        binding.eventRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.eventRecyclerView.adapter = adapter
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.eventRecyclerView)

        exposedEventAdapter = CustomerExposedEventAdapter(this)
        preferenceManager = PreferenceManager(this)
        binding.exposedEventRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.exposedEventRecyclerView.adapter = exposedEventAdapter
//        val snapHelper2 = PagerSnapHelper()
//        snapHelper2.attachToRecyclerView(binding.eventRecyclerView)


//
//
//

        val regex = Regex("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
        userViewModel.getAllCustomerEvents.observe(this) {
            if (it.data.isNotEmpty()) {
                adapter.updateList(it.data)
            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.exposedEventsTab.setOnClickListener {
            binding.exposedLayout.visibility = View.VISIBLE
            binding.eventLayout.visibility = View.GONE

            binding.exposedEventsTab.setBackgroundColor(resources.getColor(R.color.blueColorDark))
            binding.exposedEventsTab.setTextColor(resources.getColor(R.color.white))
            binding.myEventTab.setBackgroundColor(resources.getColor(R.color.white))
            binding.myEventTab.setTextColor(resources.getColor(R.color.black))
            userViewModel.eventExposedToMe(preferenceManager.getVendorId().toString())
        }



        userViewModel.eventExposedToMe.observe(this) {
            exposedListData.clear()
            if (it.data.isNotEmpty()) {
                if (date!!.matches(regex)) {
                    for (data in it.data) {
                        for (item in data.eventDates) {
                            if (item.fromDate.equals(date)) {
                                exposedListData.add(data)
                            }
                        }
                    }
                    exposedEventAdapter.updateList(exposedListData)
                } else {
                    exposedEventAdapter.updateList(it.data)
                }
            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }


        binding.myEventTab.setOnClickListener {
            binding.exposedLayout.visibility = View.GONE
            binding.eventLayout.visibility = View.VISIBLE
            binding.myEventTab.setBackgroundColor(resources.getColor(R.color.blueColorDark))
            binding.myEventTab.setTextColor(resources.getColor(R.color.white))
            binding.exposedEventsTab.setBackgroundColor(resources.getColor(R.color.white))
            binding.exposedEventsTab.setTextColor(resources.getColor(R.color.black))
        }

        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getAllEvents(preferenceManager.getVendorId().toString())
            binding.swipeToRefresh.isRefreshing = false
        }

        binding.back.setOnClickListener {
            finish()
        }

        date = intent.getStringExtra("date")
//        viewModel.getEventByDate(date!!)

        userViewModel.getEventByDate.observe(this) {
            if (it.data.isNotEmpty()) {
                adapter.updateList(it.data)
                binding.eventLayout.visibility = View.VISIBLE
            } else {
                binding.eventLayout.visibility = View.GONE
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.previous.setOnClickListener {
            if (currentPosition > 0) {
                currentPosition--
                binding.eventRecyclerView.smoothScrollToPosition(currentPosition)
            }
        }

        binding.next.setOnClickListener {
            if (currentPosition < adapter.itemCount - 1) {
                currentPosition++
                binding.eventRecyclerView.smoothScrollToPosition(currentPosition)
            }
        }

//        Log.d("date", "onCreate: $date")
    }

    fun showBottomSheet(context: Context, name: String, event: EventData, isExposed: Boolean) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null)

        val titleTextView: MaterialTextView = view.findViewById(R.id.tvTitle)
        val addPaymentTextView: MaterialTextView = view.findViewById(R.id.tvAddPayment)
        val editEventTextView: MaterialTextView = view.findViewById(R.id.tvEditEvent)
        val deleteEventTextView: MaterialTextView = view.findViewById(R.id.tvDeleteEvent)
        val exposeEvent: MaterialTextView = view.findViewById(R.id.exposeEvent)

        if (isExposed) {
            addPaymentTextView.visibility = View.GONE
            editEventTextView.visibility = View.GONE
            deleteEventTextView.visibility = View.GONE
            exposeEvent.visibility = View.VISIBLE
        } else {
            addPaymentTextView.visibility = View.VISIBLE
            editEventTextView.visibility = View.VISIBLE
            deleteEventTextView.visibility = View.VISIBLE
            exposeEvent.visibility = View.GONE
        }

        titleTextView.text = name

        var text = ""
        addPaymentTextView.text =
            "${addPaymentTextView.text} (Remaining:\u20B9${event.remainingAmount})"

        if (event.remainingAmount!! > 0 && !isExposed) {
            addPaymentTextView.visibility = View.VISIBLE
        } else {
            addPaymentTextView.visibility = View.GONE
        }
        addPaymentTextView.setOnClickListener {
            // Handle add payment click
            // Use eventId as needed
            val intent = Intent(this, AddPaymentActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        exposeEvent.setOnClickListener {
            val intent = Intent(this, ExposingEventActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }



        editEventTextView.setOnClickListener {
            // Handle edit event click
            // Use eventId as needed
            bottomSheetDialog.dismiss()
        }

        deleteEventTextView.setOnClickListener {
            // Handle delete event click
            // Use eventId as needed
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    override fun onResume() {
        if (date.toString() == "showAll") {
            userViewModel.getAllEvents(preferenceManager.getVendorId().toString())
            binding.fromPayments.visibility = View.GONE
        } else if (date.toString() == "payment") {
            binding.fromPayments.visibility = View.VISIBLE
            binding.tabLayout
                .visibility = View.GONE
            userViewModel.getAllEvents(preferenceManager.getVendorId().toString())
        } else if (date.toString().contains("From Customer")) {
            Log.d("date", date.toString())
            val customerId = date.toString().split(" ")[2]
            userViewModel.getEventByCustomer(customerId)
        } else {
            userViewModel.getEventByDate(date!!, preferenceManager.getVendorId().toString())
            binding.fromPayments.visibility = View.GONE
        }
        super.onResume()
    }

    override fun actionItemClick(position: Int, item: EventData) {
        showBottomSheet(this, item.customerdata[0].customerName!!, item, false)
    }

    override fun exposeEvent(position: Int, item: EventData) {
        showBottomSheet(this, item.customerdata[0].customerName!!, item, true)
    }
}