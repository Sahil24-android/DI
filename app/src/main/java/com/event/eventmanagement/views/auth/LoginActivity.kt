package com.event.eventmanagement.views.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.databinding.ActivityLoginBinding
import com.event.eventmanagement.extras.CustomProgressDialog
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.auth.datasource.LoginBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private val loader by lazy { CustomProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.login.setOnClickListener {
            if (binding.mobileNumber.text!!.isEmpty()) {
                binding.mobileNumberLayout.error = "Enter Mobile Number"
                binding.mobileNumber.requestFocus()
                return@setOnClickListener
            }
            if (binding.password.text!!.isEmpty()) {
                binding.passwordLayout.error = "Enter Password"
                binding.password.requestFocus()
                return@setOnClickListener
            }
            val loginBody = LoginBody(
                password = binding.password.text.toString(),
                mobileNumber = binding.mobileNumber.text.toString()
            )
            userViewModel.login(loginBody)
        }



        userViewModel.isLoading.observe(this) {
            if (it) {
                loader.show()
            } else {
                loader.dismiss()
            }
        }
        userViewModel.loginResponse.observe(this) { response ->
            if (response != null) {
                preferenceManager.setLogin(true)
                preferenceManager.setToken(response.token!!)
                preferenceManager.setVendorId(response.data?.id!!)
                preferenceManager.setUserData(response.data!!)
                preferenceManager.setImageRes(response.data?.logoImage!!)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        userViewModel.error.observe(this) {
            if (it != null) {
                Toast.makeText(this, "Please check your credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

