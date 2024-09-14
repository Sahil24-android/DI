package com.event.eventmanagement.views.activity.plans

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.R
import com.event.eventmanagement.apis.RetrofitClient
import com.event.eventmanagement.databinding.ActivitySelectPlanBinding
import com.event.eventmanagement.databinding.BillingDetailsLayoutBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.plans.adapter.ActivePlansAdapter
import com.event.eventmanagement.views.activity.profile.data.BuyPackageBody
import com.event.eventmanagement.views.activity.profile.data.CheckTransactionStatus
import com.event.eventmanagement.views.auth.datasource.PackageItem
import com.phonepe.intent.sdk.api.B2BPGRequestBuilder
import com.phonepe.intent.sdk.api.PhonePe
import com.phonepe.intent.sdk.api.PhonePeInitException
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SelectPlanActivity : AppCompatActivity(), ActivePlansAdapter.OnItemClickListener {
    private lateinit var binding: ActivitySelectPlanBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var activePlansAdapter: ActivePlansAdapter
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)
        token = "Bearer ${preferenceManager.getToken()}"
        userViewModel.getServicePackage(token,preferenceManager.getUserData()!!.serviceId.toString())

        activePlansAdapter = ActivePlansAdapter(this)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.activePlansRecyclerView.layoutManager = layoutManager
        binding.activePlansRecyclerView.adapter = activePlansAdapter
        userViewModel.servicePackages.observe(this) { result ->
            if (result.data.isNotEmpty()) {
                activePlansAdapter.updateList(result.data)
            } else {
                Toast.makeText(this, "Package Not Available", Toast.LENGTH_SHORT).show()
            }
        }

        var currentPosition = 0

        binding.previous.setOnClickListener {
            if (currentPosition > 0) {
                currentPosition--
                binding.activePlansRecyclerView.smoothScrollToPosition(currentPosition)
            }
        }

        binding.next.setOnClickListener {
            if (currentPosition < layoutManager.itemCount - 1) {
                currentPosition++
                binding.activePlansRecyclerView.smoothScrollToPosition(currentPosition)
            }
        }

        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getServicePackage(token,preferenceManager.getUserData()!!.serviceId.toString())
            binding.swipeToRefresh.isRefreshing = false
        }

    }

    override fun onItemClick(position: Int, item: PackageItem) {
//        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        billingDetailsDialog(item)

    }

    private var item:PackageItem? = null;
    private fun billingDetailsDialog(item: PackageItem) {
        val dialog = Dialog(this)
        val view = BillingDetailsLayoutBinding.inflate(layoutInflater)

        dialog.setContentView(view.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Make the dialog background transparent if needed
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_shape_background) // Set the custom background with rounded corners
        view.packageAmount.text = "\u20B9 ${item.amount}"
        val gstCalculation = ((item.amount!!) * 1.18).toInt() - item.amount!!
        view.amountAfterGst.text = "\u20B9 ${gstCalculation}"
        val finalAmount = item.amount!! + gstCalculation
        view.finalAmount.text = "\u20B9 ${finalAmount}"

        view.close.setOnClickListener {
            dialog.dismiss()
        }

        userViewModel.buyPackage.observe(this) { response ->
            if (response != null) {
                Toast.makeText(this, "Package Purchased Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finish()
            }
        }

        view.checkout.setOnClickListener {
            makePayment(finalAmount,item)
            this.item =  item
            dialog.dismiss()
        }
        dialog.show()
    }

    private val apiEndPoint = "/pg/v1/pay"
    //    private val saltKey = "d70b3d5b-1f5d-47ee-be1d-b9cf67442a25"
//    private val MERCHANT_ID = "CLUEMATRIXONLINE"
    private val saltKey =  "d23f00a5-1ced-4abb-9293-125cb1de2008"
    private val MERCHANT_ID = "CLUEMATRIXONLINE"


    private val saltIndex = "1"
    private val B2B_PG_REQUEST_CODE = 777
    private lateinit var MERCHANT_TID: String
    private var finalPayableAmount: Double = 0.0
    private lateinit var xVerify: String


    private fun makePayment(amount:Int,item: PackageItem) {
        val requestBodyJson = JSONObject()
        MERCHANT_TID = "Events" + System.currentTimeMillis()

        try {
            requestBodyJson.put("merchantId", MERCHANT_ID)
            requestBodyJson.put("merchantTransactionId", MERCHANT_TID)
            requestBodyJson.put("merchantUserId", MERCHANT_ID)
            requestBodyJson.put("amount", amount * 100)
            requestBodyJson.put("callbackUrl", "https://webhook.site/callback-url")
            requestBodyJson.put("mobileNumber", "7038012890")
            requestBodyJson.put("redirectUrl", "https://webhook.site/callback-url")
            requestBodyJson.put("redirectMode", "POST");


            val paymentInstrument = JSONObject()
            paymentInstrument.put("type", "PAY_PAGE")

            requestBodyJson.put("paymentInstrument", paymentInstrument)

            val requestBody = requestBodyJson.toString()
            val encodedBytes =
                Base64.encode(requestBody.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
            val base64Payload = String(encodedBytes)

            val checksum = sha256(base64Payload + apiEndPoint + saltKey) + "###" + saltIndex

            Log.d("checksum", "makePayment: $checksum")

            val b2BPGRequest = B2BPGRequestBuilder()
                .setData(base64Payload)
                .setChecksum(checksum)
                .setUrl(apiEndPoint)
                .build()

            try {
                PhonePe.getImplicitIntent(this, b2BPGRequest, "")
                    ?.let { startActivityForResult(it, B2B_PG_REQUEST_CODE) }
            } catch (e: PhonePeInitException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == B2B_PG_REQUEST_CODE) {
                // Handle the result from PhonePe SDK here
//                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()
                checkTransactionStatus()
            } else {
                // Payment failed or user canceled
                Log.d("TAG", "onActivityResult: $data")
                Toast.makeText(this, "Payment failed or user canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkTransactionStatus() {
        val dataToHash = "/pg/v1/status/$MERCHANT_ID/$MERCHANT_TID$saltKey"
        xVerify = sha256(dataToHash) + "###1"

        val headers = mapOf(
            "Content-Type" to "application/json",
            "X-VERIFY" to xVerify,
            "X-MERCHANT-ID" to MERCHANT_ID
        )

        val call = RetrofitClient.getPG_SDKInterface()
            .getTransactionStatus(MERCHANT_ID, MERCHANT_TID, headers)
        call.enqueue(object : Callback<CheckTransactionStatus> {
            override fun onResponse(
                call: Call<CheckTransactionStatus>,
                response: Response<CheckTransactionStatus>
            ) {
                if (response.isSuccessful && response.body() != null) {
//                    Log.d("phonepe", "onResponse: ${response.body()}")
                    if (response.body()!!.success == true) {
                        Toast.makeText(this@SelectPlanActivity, response.message(), Toast.LENGTH_SHORT)
                            .show()
                        // Process the response data as needed
                        Log.d("success", "onResponse: ${response.body()}")
                        val buyPackageBody = BuyPackageBody(
                            packageId = item?.id!!,
                            vendorId = preferenceManager.getUserData()!!.id!!,
                            packageValidity = item?.validityInDays!!,
                            purchaseDate = getCurrentDate(),
                            amount = item?.amount!!.toString(),
                            transactionId = response.body()!!.data?.transactionId!!
                        )
                        userViewModel.buyPackage(token,buyPackageBody)

                    } else {

                        Toast.makeText(this@SelectPlanActivity, response.message(), Toast.LENGTH_SHORT)
                            .show()
//                        Log.d("TAG", "onResponse: ${response.message()}")
                        Log.d("failed", "onResponse: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<CheckTransactionStatus>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun sha256(input: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
            val hexString = StringBuilder()
            for (b in hash) {
                val hex = Integer.toHexString(0xff and b.toInt())
                if (hex.length == 1) {
                    hexString.append('0')
                }
                hexString.append(hex)
            }
            hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ""
        }
    }
}