package com.event.eventmanagement.views.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.MainActivity
import com.event.eventmanagement.databinding.ActivityLoginBinding
import com.event.eventmanagement.extras.CustomProgressDialog
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.auth.datasource.LoginBody
import com.event.eventmanagement.views.fragment.datasource.CustomerResponse
import com.google.gson.annotations.SerializedName

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private val loader by lazy { CustomProgressDialog(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        preferenceManager = PreferenceManager(this)

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.login.setOnClickListener {
            if (binding.password.text!!.isNotEmpty() && binding.mobileNumber.text!!.isNotEmpty()) {
                val loginBody = LoginBody(
                    password = binding.password.text.toString(),
                    mobileNumber = binding.mobileNumber.text.toString()
                )
                userViewModel.login(loginBody)
            }else{
                Toast.makeText(this,"Please enter mobile number and password",Toast.LENGTH_SHORT).show()
            }
        }


        userViewModel.isLoading.observe(this){
            if(it){
                loader.show()
            }else{
                loader.dismiss()
            }
        }
        userViewModel.loginResponse.observe(this){response ->
            if(response != null){
                val intent = Intent(this,MainActivity::class.java)
                preferenceManager.setLogin(true)
                preferenceManager.setVendorId(response.data?.id!!)
                preferenceManager.setUserData(response.data!!)
                preferenceManager.setImageRes(response.data?.logoImage!!)
                startActivity(intent)
                finish()
            }
        }

        userViewModel.error.observe(this){
            if(it != null){
               Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
            }
        }


    }
}

