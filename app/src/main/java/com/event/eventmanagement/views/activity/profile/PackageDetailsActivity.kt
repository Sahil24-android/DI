package com.event.eventmanagement.views.activity.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.event.eventmanagement.databinding.ActivityPackageDetailsBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.plans.SelectPlanActivity
import com.phonepe.intent.sdk.api.PhonePe
import com.phonepe.intent.sdk.api.models.PhonePeEnvironment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PackageDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPackageDetailsBinding
    private val userViewModel: UserViewModel by viewModels()
    private val apiEndPoint = "/pg/v1/pay"
    private var token = ""

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityPackageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferenceManager = PreferenceManager(this)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        PhonePe.init(this, PhonePeEnvironment.RELEASE, "", "")

        token = "Bearer ${preferenceManager.getToken()}"
        binding.back.setOnClickListener {
            finish()
        }

        binding.upgradePlan.setOnClickListener {
//            makePayment()
            val intent = Intent(this, SelectPlanActivity::class.java)
            startActivity(intent)
        }

        val registrationDate = preferenceManager.getUserData()!!.registrationDate
        val currentDate = getCurrentDate()
        val daysLeft = calculateDaysBetween(registrationDate!!, currentDate)
        var totalDays = preferenceManager.getUserData()!!.packageValidity


        userViewModel.getMyPackage.observe(this) { response ->
            if (response.packagePurchase.isNotEmpty()) {
                binding.freeTrialLayout.visibility = android.view.View.GONE
                binding.paidPackageLayout.visibility = android.view.View.VISIBLE
                Log.d("TAG", "onCreate: ${response.packagePurchase}")
                val lastData = response.packagePurchase.last()
                val purchaseDate = lastData.purchaseDate
//                val currentDate = getCurrentDate()
                totalDays = response.packagePurchase.last().packageDetail[0].validityInDays
                val calculateDays = calculateDaysBetween(purchaseDate!!, currentDate)
                val remainingDays = totalDays!!.toLong().minus(calculateDays)
                binding.paidPackage.text =
                    "Package Ends in " + remainingDays + " Days"


                binding.packageName.text = lastData.packageDetail[0].packageName
                binding.packageDescription.text = lastData.packageDetail[0].description
                binding.packageAmount.text = lastData.packageDetail[0].amount.toString()
                binding.purchaseDate.text = lastData.purchaseDate
            } else {
                val intent = Intent(this@PackageDetailsActivity, SelectPlanActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun calculateDaysBetween(startDateStr: String, endDateStr: String): Long {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val startDate = sdf.parse(startDateStr)
        val endDate = sdf.parse(endDateStr)

        // Calculate the difference in milliseconds
        val differenceInMillis = endDate.time - startDate.time

        // Convert the difference from milliseconds to days
        return differenceInMillis / (1000 * 60 * 60 * 24)
    }



    override fun onResume() {
        super.onResume()
        userViewModel.getMyPackage(token,preferenceManager.getUserData()?.id!!.toString())
    }


}