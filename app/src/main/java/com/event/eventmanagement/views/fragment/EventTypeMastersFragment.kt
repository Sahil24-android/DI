package com.event.eventmanagement.views.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.R
import com.event.eventmanagement.views.auth.adapter.EventAdapter
import com.event.eventmanagement.databinding.FragmentEventTypeBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.fragment.datasource.EventBody
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class EventTypeMastersFragment : Fragment() {
    private lateinit var binding: FragmentEventTypeBinding
    private val eventsMap: MutableMap<Int, List<String>> = mutableMapOf()
    private lateinit var userViewModel: UserViewModel
    private lateinit var eventAdapter: EventAdapter
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventTypeBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        eventAdapter = EventAdapter(requireContext())
        preferenceManager = PreferenceManager(requireContext())
       // (activity as MainActivity).hideToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMaster.setOnClickListener {
            showCustomDialog()
        }


        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.eventRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.eventRecycler.adapter = eventAdapter

        userViewModel.getEvents()

        userViewModel.getAllEventsResponse.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                val response = result
                val list = response.data
                eventAdapter.updateList(list)
                Log.d("ArrayList", list.toString())
            } else {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        }

        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getEvents()
            binding.swipeToRefresh.isRefreshing = false
        }
    }


    companion object {

    }

    private fun showCustomDialog(
    ) {
        val dialogView =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.custome_event_master_dialog, null)

        val eventName = dialogView.findViewById<TextInputEditText>(R.id.eventName)
        val eventDescription = dialogView.findViewById<TextInputEditText>(R.id.eventDescription)
        val save = dialogView.findViewById<MaterialButton>(R.id.save)
        val cancel = dialogView.findViewById<MaterialButton>(R.id.cancel)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .setTitle("Event Master")
            .create()


        save.setOnClickListener {
            val eventBody = EventBody(
                vendorId = preferenceManager.getVendorId().toString(),
                eventName = eventName.text.toString(),
                description = eventDescription.text.toString()
            )
            userViewModel.addEvent(eventBody)
            userViewModel.eventResponse.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    val response = result
                    if (response.msg!!.contains("Event Added Successfully")) {
                        dialog.dismiss()
                        Handler(Looper.getMainLooper()).postDelayed({
                            userViewModel.getEvents()
                            binding.swipeToRefresh.isRefreshing = false
                        }, 1500)
                    } else {
                        Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                        Handler(Looper.getMainLooper()).postDelayed({
                            userViewModel.getEvents()
                            binding.swipeToRefresh.isRefreshing = false
                        }, 1500)
                    }
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        Log.d("called onPause", "called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("called onStop", "called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("called onStart", "called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("called onResume", "called")
    }
}