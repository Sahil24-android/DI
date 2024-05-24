package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.views.auth.adapter.EventPackageMasterAdapter
import com.event.eventmanagement.databinding.FragmentPackageMasterBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.views.activity.newpackageMaster.AddNewEventPackageMaster


class PackageMasterFragment : Fragment() {
    private lateinit var binding: FragmentPackageMasterBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var eventPackageMasterAdapter: EventPackageMasterAdapter

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
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        eventPackageMasterAdapter = EventPackageMasterAdapter()
    //    (activity as MainActivity).hideToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.eventPackageRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.eventPackageRecycler.adapter = eventPackageMasterAdapter

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        userViewModel.getEventPackages()
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
            userViewModel.getEventPackages()
            binding.swipeToRefresh.isRefreshing = false
        }
        binding.addPackageMaster.setOnClickListener {
            val intent = Intent(requireContext(), AddNewEventPackageMaster::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getEventPackages()
    }

    companion object {

    }
}