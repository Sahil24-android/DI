package com.event.eventmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.event.eventmanagement.databinding.ActivityMainBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.profile.ProfileActivity
import com.event.eventmanagement.views.fragment.AccountReportsFragment
import com.event.eventmanagement.views.fragment.DashBoardFragment
import com.event.eventmanagement.views.fragment.GalleryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager
    private val userViewModel: UserViewModel by viewModels()
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        preferenceManager = PreferenceManager(this)
        token = "Bearer ${preferenceManager.getToken()}"

//        val toggle = ActionBarDrawerToggle(
//            this, binding.drawerLayout,binding.toolbar,R.string.open_drawer,R.string.close_drawer
//        )
//        binding.drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        val header = binding.navView.getHeaderView(0)
//        val imageViewLogo = header.findViewById<ImageView>(R.id.companyLogo)
//        val profile = header.findViewById<MaterialButton>(R.id.profile)
//        Picasso.get().load("${RetrofitClient.BASE_URL.replace("api/","")}${preferenceManager.getImageUrl()}").into(imageViewLogo)

//        profile.setOnClickListener {
//            val intent = Intent(this,ProfileActivity::class.java)
//            startActivity(intent)
//        }
//        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        loadFragment(DashBoardFragment())
        binding.title.text = "DashBoard"
//        binding.navView.setNavigationItemSelectedListener { menuItem ->
//            // Handle navigation item clicks here
//            when (menuItem.itemId) {
//                R.id.dashBoard -> {
//                    // Load Fragment1 when item 1 is clicked
//                    loadFragment(DashBoardFragment())
//                    binding.title.text = "DashBoard"
//                    true
//                }
//
//                R.id.expenses ->{
//                    loadFragment(ExpensesFragment())
//                    binding.title.text = "Expenses"
//                    true
//                }
//
//                R.id.eventMaster ->{
//                    loadFragment(EventTypeMastersFragment())
//                    binding.title.text = "Event Master"
//                    true
//                }
//
//                R.id.packageMaster  ->{
//                    loadFragment(PackageMasterFragment())
//                    binding.title.text = "Package Master"
//                    true
//                }
//
////                R.id.employee ->{
////                    loadFragment(EmployeeFragment())
////                    binding.title.text = "Employee"
////                    true
////                }
//
//
//                R.id.logout ->{
//                    preferenceManager.clearSession()
//                    startActivity(Intent(this, LoginActivity::class.java))
//                    finishAffinity()
////                    startActivity(Intent(this, InvoiceActivity::class.java))
//                    true
//                }
////                R.id.socket ->{
////                    loadFragment(WebSocketGuide())
////                    binding.title.text = "Socket"
////                    true
////                }
//                // Add more cases for other items
//                else -> false
//
//
//            }
//
//        }

        binding.bottomNavigation.selectedItemId = R.id.dashBoard


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashBoard -> {
                    loadFragment(DashBoardFragment())
                    binding.title.text = "DashBoard"
                    true
                }

                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                R.id.gallery -> {
                    loadFragment(GalleryFragment())
                    binding.title.text = "Gallery"
//                    Toast.makeText(this, "Launching Soon", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.reports -> {
                    loadFragment(AccountReportsFragment())
                    binding.title.text = "Reports"
                    true
                }

                else -> {
                    false
                }
            }

        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .commit()
        //binding.drawerLayout.closeDrawers()
    }


    fun hideToolbar() {
        binding.appbar.visibility = View.GONE
    }

    fun showToolbar() {
        binding.appbar.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getMyPackage(token,preferenceManager.getUserData()?.id!!.toString())
    }


}
