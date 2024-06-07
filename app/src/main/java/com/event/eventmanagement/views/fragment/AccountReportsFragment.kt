package com.event.eventmanagement.views.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.FragmentAccountReportsBinding
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.report.data.FromToDateBody
import com.event.eventmanagement.views.fragment.adapter.TransactionSummaryAdapter
import com.event.eventmanagement.views.fragment.datasource.ReportData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AccountReportsFragment : Fragment() {
    private lateinit var binding: FragmentAccountReportsBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var transactionSummaryAdapter: TransactionSummaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountReportsBinding.inflate(inflater, container, false)
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<ReportData>()

        transactionSummaryAdapter = TransactionSummaryAdapter(requireContext())
        binding.transactionSummaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionSummaryRecyclerView.adapter = transactionSummaryAdapter


        val (startDate, endDate) = getStartAndEndDateOfCurrentMonth()
        binding.dataForDates.text = "Showing Data from $startDate to $endDate"
        val body = FromToDateBody(startDate,endDate)
        userViewModel.getReportsByDate(body,preferenceManager.getVendorId().toString())
        userViewModel.getReports.observe(viewLifecycleOwner) {
            list.clear()
            lifecycleScope.launch {
                binding.income.text = "+ ₹ ${it.data!!.sumPay.toString()}"
                binding.expense.text = "- ₹ ${it.data!!.sumExpense.toString()}"
                val totalGain = it.data!!.sumPay?.minus(it.data!!.sumExpense!!)
                binding.overallGain.text = "₹ ${totalGain.toString()}"


                withContext(Dispatchers.Default) {
                    for (item in it.data!!.getPay) {
                        list.add(
                            ReportData(
                                item.customerdata!!.customerName, item.createdAt.toString(), item.paidAmount.toString(), true
                            )
                        )
                    }


                    for (item in it.data!!.getExpense) {
                        list.add(
                            ReportData(
                                item.vendor!!.ownerName, item.createdAt.toString(), item.amount.toString(), false
                            )
                        )
                    }
                }


                withContext(Dispatchers.Main) {
                    transactionSummaryAdapter.updateList(list.sortedBy { it.date }.reversed())
                }
            }
        }


        binding.swipeToRefresh.setOnRefreshListener {
            userViewModel.getReportsByDate(body,preferenceManager.getVendorId().toString())
            binding.swipeToRefresh.isRefreshing = false
        }

        binding.filter.setOnClickListener {
            showBottomSheet(requireContext())
        }
    }


    companion object {

    }

    fun showBottomSheet(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.filter_bottom_sheet, null)
        val fromDate = view.findViewById<MaterialTextView>(R.id.fromDate)
        val toDate = view.findViewById<MaterialTextView>(R.id.toDate)
        val apply = view.findViewById<MaterialButton>(R.id.search)

        val (startDate, endDate) = getStartAndEndDateOfCurrentMonth()
        fromDate.text = startDate
        toDate.text = endDate

        fromDate.setOnClickListener {
            AppUtils.showDatePickerDialog(context,fromDate)
        }

        toDate.setOnClickListener {
            AppUtils.showDatePickerDialog(context,toDate)
        }

        apply.setOnClickListener {
            val body = FromToDateBody(fromDate.text.toString(),toDate.text.toString())
            userViewModel.getReportsByDate(body,preferenceManager.getVendorId().toString())
            binding.dataForDates.text = "Showing Data from $startDate to $endDate"

            bottomSheetDialog.dismiss()
        }


        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
    fun getStartAndEndDateOfCurrentMonth(): Pair<String, String> {
        // Create a calendar instance and set it to the current date
        val calendar = Calendar.getInstance()

        // Set the calendar to the first day of the current month
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.time

        // Set the calendar to the last day of the current month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val lastDayOfMonth = calendar.time

        // Define the desired date format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.US)

        // Format the dates
        val startDate = dateFormat.format(firstDayOfMonth)
        val endDate = dateFormat.format(lastDayOfMonth)

        return Pair(startDate, endDate)
    }
}