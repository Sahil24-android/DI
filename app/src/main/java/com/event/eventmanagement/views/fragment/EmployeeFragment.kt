package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.FragmentEmployeeBinding
import com.event.eventmanagement.model.LocationViewModel
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.views.activity.addEmployee.AddEmployeeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeBinding
    private val userViewModel: UserViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addEmployeesFab.setOnClickListener {
            val intent = Intent(requireContext(), AddEmployeeActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {

    }
}