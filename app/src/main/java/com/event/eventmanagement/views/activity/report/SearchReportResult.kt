package com.event.eventmanagement.views.activity.report

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ActivitySearchReportResultBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.report.data.FromToDateBody
import com.event.eventmanagement.views.fragment.adapter.TransactionSummaryAdapter
import com.event.eventmanagement.views.fragment.datasource.ReportData

class SearchReportResult : AppCompatActivity() {

    private lateinit var binding: ActivitySearchReportResultBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var transactionSummaryAdapter: TransactionSummaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivitySearchReportResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        preferenceManager = PreferenceManager(this)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val list = ArrayList<ReportData>()

        transactionSummaryAdapter = TransactionSummaryAdapter(this)
        binding.transactionSummaryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionSummaryRecyclerView.adapter = transactionSummaryAdapter

        val fromToDateBody = FromToDateBody("2024-05-31","2024-05-31")
        userViewModel.getReportsByDate(fromToDateBody,preferenceManager.getVendorId().toString())

    }
}