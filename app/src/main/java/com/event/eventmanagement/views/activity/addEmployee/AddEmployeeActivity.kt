package com.event.eventmanagement.views.activity.addEmployee

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivityAddEmployeeBinding
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.extras.CustomProgressDialog
import com.event.eventmanagement.model.LocationViewModel
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEmployeeBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private val locationViewModel: LocationViewModel by viewModels()
    private val dialog by lazy { CustomProgressDialog(this) }
    private val token by lazy { preferenceManager.getToken().toString() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


        binding.selectPinCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
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

        locationViewModel.isLoading.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()

            }
        }

        binding.saveEmployee.setOnClickListener {
            val validationInputLayouts = listOf(
                binding.employeeName,
                binding.employeeMobileNo,
                binding.address,
                binding.employeeAadharNo,
                binding.selectPinCode,
                binding.salaryAmount,
                binding.accountNumber,
                binding.ifscCode,
                binding.branchName
            )

            if (validateEditTexts(validationInputLayouts, "Field cannot be empty")) {
                if (binding.dateOfBirth.text.toString().trim().isNotEmpty()) {
                    if (binding.selectSalaryType.text.toString().trim().isNotEmpty()) {
                        if (binding.bankName.text.toString().trim().isNotEmpty()) {
                            val employeeBody = EmployeeBody(
                                vendorId = preferenceManager.getVendorId().toString(),
                                employeeName = binding.employeeName.text.toString(),
                                dob = binding.dateOfBirth.text.toString(),
                                anniversaryDate = if (binding.anniversaryDate.text.isEmpty()) {
                                    ""
                                } else {
                                    binding.anniversaryDate.text.toString()
                                },
                                adharNo = binding.employeeAadharNo.text.toString(),
                                mobNo = binding.employeeMobileNo.text.toString(),
                                altMobNo = if (binding.employeeAlternateMobileNo.text!!.isEmpty()) {
                                    ""
                                } else {
                                    binding.employeeAlternateMobileNo.text.toString()
                                },
                                address = binding.address.text.toString(),
                                pincode = binding.selectPinCode.text.toString(),
                                countryId = binding.country.text.toString(),
                                stateId = binding.state.text.toString(),
                                cityId = binding.city.text.toString(),
                                salaryType = binding.selectSalaryType.text.toString(),
                                salaryAmount = binding.salaryAmount.text.toString(),
                                bankName = binding.bankName.text.toString(),
                                accountNo = binding.accountNumber.text.toString(),
                                ifscCode = binding.ifscCode.text.toString(),
                                branchName = binding.branchName.text.toString()
                            )

                            userViewModel.addEmployee(token,employeeBody)
                        } else {
                            Toast.makeText(this, "Select Bank Name", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Select Salary Type", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Fill DOB", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.dateOfBirth.setOnClickListener {
            AppUtils.showDatePickerDialog(this, binding.dateOfBirth)
        }


        binding.anniversaryDate.setOnClickListener {
            AppUtils.showDatePickerDialog(this, binding.anniversaryDate)
        }



        userViewModel.addEmployee.observe(this) {
            if (it != null) {
                Toast.makeText(this, "Employee added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }


        locationViewModel.locationData.observe(this) { result ->
            if (result != null) {
                val getFirstData = result.get(0)
                val data = getFirstData.PostOffice.get(0)
                binding.state.text = data.State
                binding.city.text = data.Block
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

    private fun validateEditTexts(
        editTexts: List<TextInputEditText>,
        errorMessage: String
    ): Boolean {
        var isValid = true
        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                editText.error = errorMessage
                isValid = false
            } else {
                editText.error = null // Clear the error if not empty
            }
        }
        return isValid
    }

}