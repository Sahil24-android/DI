package com.event.eventmanagement.views.activity.addEmployee

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityAddEmployeeBinding
import com.event.eventmanagement.model.UserViewModel

class AddEmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEmployeeBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.selectPinCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 6) {
                    userViewModel.getLocationsData(s.toString())
                } else {
                    binding.state.text = null
                    binding.city.text = null
                    binding.country.text = null
                }
            }

        })


        userViewModel.locationData.observe(this) { result ->
            if (result != null) {
                val getFirstData = result.get(0)
                val data = getFirstData.PostOffice.get(0)
                binding.state.text = data.State
                binding.city.text = data.Region
                binding.country.text = data.Country
            } else {
                binding.state.text = null
                binding.city.text = null
                binding.country.text = null
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        val stringArray: ArrayList<String> = ArrayList()
        stringArray.apply {
            add("Hour Wise")
            add("Day Wise")
            add("Month Wise")
            add("Event Package Wise")
        }

        val adapter = ArrayAdapter<String>(
            this@AddEmployeeActivity, R.layout.dropdown_item, stringArray
        )
        binding.selectSalaryType.setAdapter(adapter)
        val maharashtraBankNames = listOf(
            "State Bank of India",
            "HDFC Bank",
            "ICICI Bank",
            "Axis Bank",
            "Kotak Mahindra Bank",
            "IndusInd Bank",
            "Yes Bank",
            "Bank of Maharashtra",
            "Punjab National Bank",
            "Bank of Baroda",
            "Canara Bank",
            "Union Bank of India",
            "IDFC First Bank",
            "Federal Bank",
            "Bank of India",
            "Central Bank of India",
            "Indian Bank",
            "IDBI Bank",
            "UCO Bank",
            "Indian Overseas Bank",
            "RBL Bank"
        )

        val adapter2 = ArrayAdapter<String>(
            this@AddEmployeeActivity, R.layout.dropdown_item, maharashtraBankNames
        )
        binding.bankName.setAdapter(adapter2)
    }
}