package com.event.eventmanagement.views.fragment.socket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.FragmentWebSocketGuideBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket


class WebSocketGuide : Fragment() {
    private lateinit var binding: FragmentWebSocketGuideBinding
    private lateinit var webSocketListener: WebSocketListener
    private lateinit var viewModel: MainViewModel
    private val okHttpClient = OkHttpClient()
    private var webscocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebSocketGuideBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        webSocketListener = WebSocketListener(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.socketStatus.observe(viewLifecycleOwner) {
            if (it) {
//                binding.tvStatus.text = "Connected"
                Log.d("connected","connected")
            } else {
//                binding.tvStatus.text = "Disconnected"
                Log.d("disconnected","disconnected")
            }
        }
        binding.btnConnect.setOnClickListener {
            webscocket = okHttpClient.newWebSocket(createRequest(), webSocketListener)
        }
        var text = ""
        viewModel.messages.observe(viewLifecycleOwner) {
            text += "${if (it.first) "You: " else "Other: "} ${it.second}\n"

            binding.messageTV.text = text
        }


        binding.btnSend.setOnClickListener {
            val msg = binding.inputMessage.text.toString()
            webscocket?.send(msg)
            viewModel.addMessage(Pair(true, msg))
            binding.inputMessage.setText("")
        }
        binding.btnDisconnect.setOnClickListener {
            webscocket?.close(1000, null)
//            viewModel.socketStatus.value = false
        }
    }

    private fun createRequest(): Request {
        val webSocketUrl = "wss://free.blr2.piesocket.com/v3/1?api_key=zr8PhUVQfhYYEZU56vfGWzjt8dWnjadhi7S0mcOE&notify_self=1"
        return Request.Builder().url(webSocketUrl).build()

    }


    companion object {

    }
}