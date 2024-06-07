package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.databinding.FragmentCustomerBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.views.activity.customerEventList.EventActivity
import com.event.eventmanagement.views.fragment.adapter.CustomerDetailsAdapter
import com.event.eventmanagement.views.fragment.datasource.CustomerDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerFragment : Fragment(), CustomerDetailsAdapter.OnCustomerClickListener {
    private lateinit var binding: FragmentCustomerBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter: CustomerDetailsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerBinding.inflate(inflater, container, false)
        adapter = CustomerDetailsAdapter(this)
        (activity as MainActivity).hideToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getAllCustomers()
        binding.customerDetailsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.customerDetailsRv.adapter = adapter

        userViewModel.getCustomerResponse.observe(viewLifecycleOwner){response ->
            if (response != null){
                if (response.data.isNotEmpty()){
                    adapter.updateList(response.data)
                }else{
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }
    companion object {

    }

    override fun onItemClick(position: Int, item: CustomerDetails) {
        val intent = Intent(requireContext(),EventActivity::class.java)
        intent.putExtra("date","From Customer ${item.id}")
        startActivity(intent)
    }
}