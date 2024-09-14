package com.event.eventmanagement.views.activity.employeeExpense

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.event.eventmanagement.databinding.ActivityAddEmployeeExpenseBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.addEmployee.EmployeeBody
import com.event.eventmanagement.views.activity.vendorExpense.data.EmployeeExpenseBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEmployeeExpense : AppCompatActivity() {
    private lateinit var binding: ActivityAddEmployeeExpenseBinding
    private val userViewModel : UserViewModel by viewModels()
    private var employeeId = 0
    private lateinit var preferenceManager: PreferenceManager
    private val employeeList = mutableListOf<EmployeeBody>()

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   enableEdgeToEdge()
        binding = ActivityAddEmployeeExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        preferenceManager = PreferenceManager(this)
        token = "Bearer ${preferenceManager.getToken()}"

        binding.back.setOnClickListener {
            finish()
        }

        userViewModel.getEmployee(token,preferenceManager.getVendorId().toString())

        userViewModel.getEmployee.observe(this) { response ->
            employeeList.clear()
            if (response.data.isNotEmpty()) {
                employeeList.addAll(response.data)
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
                    val listFilter = employeeList.filter { it.mobNo == s.toString() }
                    if (listFilter.isNotEmpty()) {
                        binding.employeeDetailLayout.visibility = View.VISIBLE
                        binding.employeeName.text = listFilter[0].employeeName
                        binding.employeeAddress.text = listFilter[0].address
                        binding.employeeMobileNumber.text = "${listFilter[0].mobNo}"
                        binding.employeeDob.text = listFilter[0].dob
                        binding.employeeAlternateMobileNo.text = listFilter[0].altMobNo
                        binding.employeeSalaryType.text = listFilter[0].salaryType
                        binding.employeeSalaryAmount.text = listFilter[0].salaryAmount
                        binding.employeeBankName.text = listFilter[0].bankName
                        binding.employeeAccountNumber.text = listFilter[0].accountNo
                        binding.employeeBankIfsc.text= listFilter[0].ifscCode
                        employeeId = listFilter[0].id!!

                    } else {
                        binding.employeeDetailLayout.visibility = View.GONE
                        binding.employeeName.text = null
                        binding.employeeAddress.text = null
                        binding.employeeMobileNumber.text = null
                        binding.employeeDob.text = null
                        binding.employeeAlternateMobileNo.text = null
                        binding.employeeSalaryType.text = null
                        binding.employeeSalaryAmount.text = null
                        binding.employeeBankName.text = null
                        binding.employeeAccountNumber.text = null
                        binding.employeeBankIfsc.text= null
                        employeeId = 0
                        Toast.makeText(
                            this@AddEmployeeExpense, "Vendor Not Found", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@AddEmployeeExpense, "Vendor Not Found", Toast.LENGTH_SHORT
                    ).show()
                    // binding.vendorDetailLayout.visibility = View.GONE
                    binding.employeeName.text = null
                    binding.employeeAddress.text = null
                    binding.employeeMobileNumber.text = null
                    binding.employeeDob.text = null
                    binding.employeeAlternateMobileNo.text = null
                    binding.employeeSalaryType.text = null
                    binding.employeeSalaryAmount.text = null
                    binding.employeeBankName.text = null
                    binding.employeeAccountNumber.text = null
                    binding.employeeBankIfsc.text= null
                    employeeId = 0
                }
            }


        })

        binding.addEmployeeExpense.setOnClickListener {
            if (employeeId==0 || binding.amount.text.toString().isEmpty()){
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                val body = EmployeeExpenseBody(
                    vendorId = preferenceManager.getVendorId().toString().toInt(),
                    employeeId = employeeId,
                    amount = binding.amount.text.toString().toInt()
                )
                userViewModel.addEmployeeExpense(token,body)
            }
        }

        userViewModel.employeeExpenseResponse.observe(this) { response ->
            if (response.expense != null){
                Toast.makeText(this, "Expense Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Expense Not Added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}