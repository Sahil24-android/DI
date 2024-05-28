package com.event.eventmanagement.views.activity.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.event.eventmanagement.R
import com.event.eventmanagement.apis.RetrofitClient
import com.event.eventmanagement.databinding.ActivityProfileBinding
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.auth.LoginActivity
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        preferenceManager = PreferenceManager(this)

        //    binding.number.text = preferenceManager.getUserData()!!.mobNo

        binding.back.setOnClickListener {
            finish()
        }

        Picasso.get().load(
            "${
                RetrofitClient.BASE_URL.replace(
                    "api/",
                    ""
                )
            }${preferenceManager.getImageUrl()}"
        ).into(binding.companyLogo)

        binding.ownerName.text = preferenceManager.getUserData()?.ownerName
        binding.companyName.text = preferenceManager.getUserData()?.companyName

        binding.logout.setOnClickListener {
            preferenceManager.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }


    }
}