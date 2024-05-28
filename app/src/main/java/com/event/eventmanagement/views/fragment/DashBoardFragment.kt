package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityMainBinding
import com.event.eventmanagement.databinding.FragmentDashBoardBinding
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.extras.CustomProgressDialog
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.createCustomerEvent.AddNewEventActivity
import com.event.eventmanagement.views.activity.customerEventList.EventActivity
import com.google.android.material.datepicker.DayViewDecorator
import com.sabpaisa.gateway.android.sdk.SabPaisaGateway
import com.sabpaisa.gateway.android.sdk.interfaces.IPaymentSuccessCallBack
import com.sabpaisa.gateway.android.sdk.models.TransactionResponsesModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DashBoardFragment : Fragment(), IPaymentSuccessCallBack<TransactionResponsesModel> {

    private lateinit var binding: FragmentDashBoardBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private val loader by lazy { CustomProgressDialog(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        (activity as MainActivity).showToolbar()
        preferenceManager = PreferenceManager(requireContext())

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.calendarView.setForwardButtonImage(resources.getDrawable(R.drawable.baseline_arrow_forward_ios_24))
        binding.calendarView.setPreviousButtonImage(resources.getDrawable(R.drawable.baseline_arrow_back_ios_new_24))


        //  userViewModel.getAllEventDates()
        val events: MutableList<CalendarDay> = ArrayList()
        userViewModel.allEventsDates.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.data.isNotEmpty()) {
                    //  val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    for (item in response.data) {
                        Log.d("item", item.toString())
                        val day = item.fromDate!!.split("-")
                        val calendar1 = Calendar.getInstance()
                        calendar1.set(Calendar.DAY_OF_MONTH, day[2].toInt())
                        calendar1.set(Calendar.MONTH, day[1].toInt() - 1)
                        val calendarDay1 = CalendarDay(calendar1)
                        calendarDay1.backgroundResource = (R.drawable.solid_color)
                        calendarDay1.labelColor = R.color.white
                        events.add(calendarDay1)
                    }
                    Log.d("events", events.toString())
                    binding.calendarView.setCalendarDays(events)
                } else {
                    Toast.makeText(requireContext(), "No Event Found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        userViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                loader.show()
            } else {
                loader.dismiss()
            }
        }




        binding.customers.setOnClickListener {
            loadFragment(CustomerFragment())
        }

//        binding.gallery.setOnClickListener {
//            val sabPaisaGateway1 =
//                SabPaisaGateway.builder()
//                    .setAmount(1.0)   //Mandatory Parameter
//                    .setFirstName("Bhargav") //Mandatory Parameter
//                    .setLastName("B") //Mandatory Parameter
//                    .setIntermediateLoading(false)
//                    .setMobileNumber("1234556644") //Mandatory Parameter
//                    .setEmailId("test@gamil.com")//Mandatory Parameter
//                    .setClientCode("WAHU75")     // Please use the credentials shared by your Account Manager  If not, please contact your Account Manage
//                    .setSabPaisaPaymentScreen(true)
//
//                    .setAesApiIv("55QVm8EUD4Jmmyhj")   // Please use the credentials shared by your Account Manager  If not, please contact your Account Manage
//                    .setAesApiKey("VfTe9AbISD9FRsUO")   // Please use the credentials shared by your Account Manager  If not, please contact your Account Manage
//                    .setTransUserName("Wadhuwar75_16187")      // Please use the credentials shared by your Account Manager  If not, please contact your Account Manage
//                    .setTransUserPassword("WAHU75_SP16187")   // Please use the credentials shared by your Account Manager  If not, please contact your Account Manage
//                    .build()
//
//            SabPaisaGateway.setInitUrl("https://securepay.sabpaisa.in/SabPaisa/sabPaisaInit?v=1")
//            SabPaisaGateway.setEndPointBaseUrl("https://securepay.sabpaisa.in")
//            SabPaisaGateway.setTxnEnquiryEndpoint("https://txnenquiry.sabpaisa.in")
//
//            sabPaisaGateway1.init(requireActivity(),this)
//        }

        binding.packageMaster.setOnClickListener {
            loadFragment(PackageMasterFragment())
        }

        binding.payments.setOnClickListener {
            val intent = Intent(requireContext(), EventActivity::class.java)
            intent.putExtra("date", "payment")
            startActivity(intent)
        }


        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getAllEventDates(preferenceManager.getVendorId().toString())
            binding.swipeToRefresh.isRefreshing = false
        }

//        val calendar1 = Calendar.getInstance()
//        calendar1.set(Calendar.DAY_OF_MONTH, 7)
//        val calendarDay1 = CalendarDay(calendar1)
//        calendarDay1.imageResource = (R.drawable.solid_color)
//        events.add(calendarDay1)
//
//
//        val calendar2 = Calendar.getInstance()
//        calendar2.set(Calendar.DAY_OF_MONTH, 14)
//        val calendarDay2 = CalendarDay(calendar2)
//        calendarDay2.imageResource = (R.drawable.solid_color)
//        events.add(calendarDay2)
//
//
//        val calendar3 = Calendar.getInstance()
//        calendar3.set(Calendar.DAY_OF_MONTH, 21)
//        val calendarDay3 = CalendarDay(calendar3)
//
//        events.add(calendarDay3)


        binding.transfer.setOnClickListener {
            loadFragment(ExposeEventFragment())
        }


        binding.calendarView.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
            override fun onClick(calendarDay: CalendarDay) {
                val exist = events.find { it.calendar == calendarDay.calendar }
                if (exist != null) {
                    Toast.makeText(requireContext(), "Event Found", Toast.LENGTH_SHORT).show()
                    val date = calendarDay.calendar.time
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val formattedDate = dateFormat.format(date)
                    val intent = Intent(requireContext(), EventActivity::class.java)
                    intent.putExtra("date", formattedDate)
                    startActivity(intent)
                }
                // Toast.makeText(requireContext(),"${calendarDay}",Toast.LENGTH_SHORT).show()
            }
        })

        binding.addEvent.setOnClickListener {
            val intent = Intent(requireContext(), AddNewEventActivity::class.java)
            startActivity(intent)
        }



        binding.showAllEvents.setOnClickListener {
            val intent = Intent(requireContext(), EventActivity::class.java)
            intent.putExtra("date", "showAll")
            startActivity(intent)
        }

        binding.eventMaster.setOnClickListener {
            loadFragment(EventTypeMastersFragment())

        }

        binding.packageMaster.setOnClickListener {
            loadFragment(PackageMasterFragment())

        }

    }

    override fun onResume() {
        super.onResume()
        userViewModel.getAllEventDates(preferenceManager.getVendorId().toString())
    }

    companion object {

    }

    private fun loadFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onPaymentFail(message: TransactionResponsesModel?) {
        Log.d("SABPAISA", "Payment Fail")
    }

    override fun onPaymentSuccess(response: TransactionResponsesModel?) {
        Log.d("SABPAISA", "Payment Success${response?.statusCode}")
    }
}