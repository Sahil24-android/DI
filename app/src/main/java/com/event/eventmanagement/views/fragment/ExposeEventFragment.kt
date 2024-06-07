package com.event.eventmanagement.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.databinding.FragmentExposeEventBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.fragment.adapter.ExposedEventAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExposeEventFragment : Fragment() {

    private lateinit var binding:FragmentExposeEventBinding
    private lateinit var exposedEventAdapter: ExposedEventAdapter
    private val userViewModel: UserViewModel by viewModels()
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
       binding = FragmentExposeEventBinding.inflate(inflater,container,false)
        preferenceManager = PreferenceManager(requireContext())
        (activity as MainActivity).hideToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        exposedEventAdapter = ExposedEventAdapter(requireContext())
        binding.exposeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.exposeRecycler.adapter = exposedEventAdapter

        userViewModel.eventExposedByMe(preferenceManager.getVendorId().toString())

        userViewModel.eventExposedByMe.observe(viewLifecycleOwner){response ->
            if (response.data.isNotEmpty()){
                exposedEventAdapter.updateList(response.data)
            }
        }


        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.eventExposedByMe(preferenceManager.getVendorId().toString())
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    companion object {

    }
}