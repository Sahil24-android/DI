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
import com.event.eventmanagement.views.auth.adapter.EventPackageMasterAdapter
import com.event.eventmanagement.databinding.FragmentPackageMasterBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.newpackageMaster.AddNewEventPackageMaster
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PackageMasterFragment : Fragment() {
    private lateinit var binding: FragmentPackageMasterBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var eventPackageMasterAdapter: EventPackageMasterAdapter
    private var vendorId:String? = null
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPackageMasterBinding.inflate(inflater, container, false)
        eventPackageMasterAdapter = EventPackageMasterAdapter()
        preferenceManager = PreferenceManager(requireContext())
        (activity as MainActivity).hideToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.eventPackageRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.eventPackageRecycler.adapter = eventPackageMasterAdapter

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        token = "Bearer ${preferenceManager.getToken()}"

        vendorId = preferenceManager.getVendorId().toString()
        userViewModel.getEventPackages(token,vendorId!!)
        userViewModel.getEventPackages.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                if (result.data.isNotEmpty()) {
                    val list = result.data
                    eventPackageMasterAdapter.updateList(list)
                } else {
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getEventPackages(token,vendorId!!)
            binding.swipeToRefresh.isRefreshing = false
        }
        binding.addPackageMaster.setOnClickListener {
            val intent = Intent(requireContext(), AddNewEventPackageMaster::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getEventPackages(token,vendorId!!)
    }

    companion object {

    }
}