package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.FragmentEmployeeBinding
import com.event.eventmanagement.model.LocationViewModel
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.addEmployee.AddEmployeeActivity
import com.event.eventmanagement.views.fragment.adapter.EmployeeAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeBinding
    private val userViewModel: UserViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var employeeAdapter: EmployeeAdapter
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeBinding.inflate(inflater, container, false)
        (activity as MainActivity).hideToolbar()
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addEmployeesFab.setOnClickListener {
            val intent = Intent(requireContext(), AddEmployeeActivity::class.java)
            startActivity(intent)
        }

        token = "Bearer ${preferenceManager.getToken()}"

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


    //    userViewModel.getEmployee(preferenceManager.getVendorId().toString())

        employeeAdapter = EmployeeAdapter()
        binding.employeeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.employeeRecyclerView.adapter = employeeAdapter


        userViewModel.getEmployee.observe(viewLifecycleOwner) {
            if (it.data.isEmpty()) {
                binding.employeeRecyclerView.visibility = View.GONE
                binding.noEmployeeTextView.visibility = View.VISIBLE
            } else {
                binding.employeeRecyclerView.visibility = View.VISIBLE
                binding.noEmployeeTextView.visibility = View.GONE
                employeeAdapter.updateList(it.data)

            }

        }


    }

    override fun onResume() {
        userViewModel.getEmployee(token,preferenceManager.getVendorId().toString())

        super.onResume()
    }

    companion object {

    }
}