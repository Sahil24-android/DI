package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.FragmentEmployeeBinding
import com.event.eventmanagement.views.activity.addEmployee.AddEmployeeActivity


class EmployeeFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeBinding
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