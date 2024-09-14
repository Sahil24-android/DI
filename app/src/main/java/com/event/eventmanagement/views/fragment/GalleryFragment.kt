package com.event.eventmanagement.views.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.event.eventmanagement.databinding.FragmentGallaryBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.gallery.AddGalleryImagesActivity
import com.event.eventmanagement.views.fragment.adapter.GalleryAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.addHeaderLenient

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGallaryBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private var vendorId: String = ""
    private lateinit var galleryAdapter: GalleryAdapter
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
        // Inflate the layout for this fragment
        binding = FragmentGallaryBinding.inflate(inflater, container, false)
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addGalleryImages.setOnClickListener {
            startActivity(Intent(requireContext(), AddGalleryImagesActivity::class.java))
        }

        vendorId = preferenceManager.getVendorId().toString()
        token = "Bearer ${preferenceManager.getToken()}"

        galleryAdapter = GalleryAdapter()
        binding.galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.galleryRecyclerView.adapter = galleryAdapter


        userViewModel.getGallery(token,vendorId)
        userViewModel.getGallery.observe(viewLifecycleOwner){response ->
            if (response.vendor.isNotEmpty()){
                val list = response.vendor
                galleryAdapter.updateList(list)
            }
        }

        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getGallery(token,vendorId)
            binding.swipeToRefresh.isRefreshing = false
        }

    }

    companion object {

    }
}