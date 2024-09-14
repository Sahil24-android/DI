package com.event.eventmanagement.views.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.R
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.databinding.FragmentExpensesFrgmentBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.customerEventList.data.ExpensePayment
import com.event.eventmanagement.views.activity.employeeExpense.AddEmployeeExpense
import com.event.eventmanagement.views.activity.vendorExpense.AddVendorExpense
import com.event.eventmanagement.views.fragment.adapter.EmployeeExpensesAdapter
import com.event.eventmanagement.views.fragment.adapter.VendorExpensesAdapter
import com.event.eventmanagement.views.fragment.datasource.EventBody
import com.event.eventmanagement.views.fragment.datasource.ExpenseData
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ExpensesFragment : Fragment() {
    private lateinit var binding: FragmentExpensesFrgmentBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var vendorExpensesAdapter: VendorExpensesAdapter
    private lateinit var employeeExpensesAdapter: EmployeeExpensesAdapter
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpensesFrgmentBinding.inflate(inflater, container, false)
        preferenceManager = PreferenceManager(requireContext())

        (activity as MainActivity).hideToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.addExpenses.setOnClickListener {
//            showCustomDialog()
//        }

        token = "Bearer ${preferenceManager.getToken()}"

        binding.toVendorTab.setOnClickListener {
            binding.toVendorLayout.visibility = View.VISIBLE
            binding.toEmployeesLayout.visibility = View.GONE
            binding.toVendorTab.setBackgroundColor(resources.getColor(R.color.blueColorDark))
            binding.toVendorTab.setTextColor(getResources().getColor(R.color.white))
            binding.toEmployeesTab.setBackgroundColor(resources.getColor(R.color.white))
            binding.toEmployeesTab.setTextColor(getResources().getColor(R.color.black))
            userViewModel.getVendorExpenses(token,preferenceManager.getVendorId().toString(), "vendor")
        }

        binding.toEmployeesTab.setOnClickListener {
            binding.toVendorLayout.visibility = View.GONE
            binding.toEmployeesLayout.visibility = View.VISIBLE
            binding.toVendorTab.setBackgroundColor(resources.getColor(R.color.white))
            binding.toVendorTab.setTextColor(getResources().getColor(R.color.black))
            binding.toEmployeesTab.setBackgroundColor(resources.getColor(R.color.blueColorDark))
            binding.toEmployeesTab.setTextColor(getResources().getColor(R.color.white))
            userViewModel.getEmployeeExpenses(token,preferenceManager.getVendorId().toString(), "emp")
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }



        binding.addVendorExpense.setOnClickListener {
            startActivity(Intent(requireContext(), AddVendorExpense::class.java))
        }
        val vendorExpenses = ArrayList<ExpensePayment>()
        vendorExpensesAdapter = VendorExpensesAdapter()
        binding.vendorExpenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.vendorExpenseRecyclerView.adapter = vendorExpensesAdapter



        userViewModel.getVendorExpense.observe(viewLifecycleOwner) { result ->
            vendorExpenses.clear()
            if (result.data.isNotEmpty()) {
                vendorExpensesAdapter.updateList(result.data)
            } else {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        employeeExpensesAdapter = EmployeeExpensesAdapter()
        binding.employeeExpenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.employeeExpenseRecyclerView.adapter = employeeExpensesAdapter


        userViewModel.getEmployeeExpense.observe(viewLifecycleOwner) { result ->
            vendorExpenses.clear()
            if (result.data.isNotEmpty()) {
                employeeExpensesAdapter.updateList(result.data)
            } else {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addExployeeExpense.setOnClickListener {
            startActivity(Intent(requireContext(), AddEmployeeExpense::class.java))
        }


    }

    override fun onResume() {
        userViewModel.getVendorExpenses(token,preferenceManager.getVendorId().toString(), "vendor")
        userViewModel.getEmployeeExpenses(token,preferenceManager.getVendorId().toString(), "emp")

        super.onResume()
    }

    companion object {

    }
}