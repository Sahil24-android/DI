package com.event.eventmanagement.views.activity.addeventPayments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityAddPaymentBinding
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.customerEventList.adapter.PaymentsAdapter
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.event.eventmanagement.views.activity.invoice.InvoiceActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentBinding
    private val userViewModel:UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())

        val eventData = intent.getParcelableExtra<EventData>("event")
        preferenceManager = PreferenceManager(this)


        val adapter = PaymentsAdapter(this)
        binding.paymentRecycler.adapter = adapter
        binding.paymentRecycler.layoutManager = LinearLayoutManager(this)
        adapter.updateList(eventData!!.eventPayment)
        binding.paymentRecycler.setHasFixedSize(true)
        var remainingAmount :Int? = 0
        var amount :Int? = 0
        binding.savePayment.setOnClickListener {
            val eventId = eventData.id
            amount = binding.amount.text.toString().toInt()
            val paymentDate = binding.paymentDate.text.toString()
            remainingAmount = eventData.remainingAmount?.minus(amount!!)
            val paymentBody = PaymentBody(
                preferenceManager.getVendorId().toString(),
                eventId,
                amount,
                remainingAmount,
                paymentDate,""
            )


            userViewModel.addPayment(paymentBody)

        }

        userViewModel.addPayment.observe(this) {
            if (it != null) {
                val intent = Intent(this, InvoiceActivity::class.java)
                intent.putExtra("firstReceipt",false)
                intent.putExtra("event", eventData)
                intent.putExtra("paidAmount", amount.toString())
                intent.putExtra("remaining", remainingAmount.toString())
                intent.putExtra("paymentId",it.Event?.id)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }

        binding.paymentDate.setOnClickListener {
            AppUtils.showDatePickerDialog(this, binding.paymentDate)
        }

        binding.paymentDate.setText(currentDate)

        binding.back.setOnClickListener {
            finish()
        }


        binding.eventPackageName.text = eventData!!.eventPkg.get(0).packageName
        binding.finalAmount.text = eventData.finalAmount.toString()
        binding.remainingAmount.text = eventData.remainingAmount.toString()

        binding.showPayments.setOnClickListener {

            if (binding.paymentsLayout.visibility == View.VISIBLE) {
                binding.paymentsLayout.visibility = View.GONE
                binding.showPayments.text = "Show Pays" // Update button text if needed
            } else {
                binding.paymentsLayout.visibility = View.VISIBLE
                binding.showPayments.text = "Hide Pays" // Update button text if needed
            }


        }
        binding.amount.setText(eventData.remainingAmount.toString())
        binding.lessAmount.text =
            getString(R.string.enter_amount_less_than, eventData.remainingAmount.toString())
        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty() && s.toString()
                        .toInt() <= eventData.remainingAmount!!
                ) {
                    binding.savePayment.visibility = View.VISIBLE
                    binding.lessAmount.visibility = View.GONE
                } else {
                    binding.savePayment.visibility = View.GONE
                    binding.lessAmount.visibility = View.VISIBLE
                }
            }

        })


    }
}