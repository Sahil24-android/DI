package com.event.eventmanagement.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.FragmentAccountReportsBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager


class AccountReportsFragment : Fragment() {
    private lateinit var binding: FragmentAccountReportsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountReportsBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getReports(preferenceManager.getVendorId().toString())

        userViewModel.getReports.observe(viewLifecycleOwner){
            binding.income.text = "₹ ${it.data!!.sumPay.toString()}"
            binding.expense.text = "₹ ${it.data!!.sumExpense.toString()}"
        }

    }

    companion object {

    }
}