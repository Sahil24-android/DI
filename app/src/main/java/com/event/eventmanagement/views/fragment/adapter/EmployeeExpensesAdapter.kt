package com.event.eventmanagement.views.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.event.eventmanagement.R
import com.event.eventmanagement.databinding.ExpenseDataItemBinding
import com.event.eventmanagement.views.activity.customerEventList.data.ExpensePayment
import com.event.eventmanagement.views.fragment.datasource.ExpenseData

class EmployeeExpensesAdapter() :
    RecyclerView.Adapter<EmployeeExpensesAdapter.VendorExpensesViewHolder>() {

    private val list: ArrayList<ExpenseData> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeExpensesAdapter.VendorExpensesViewHolder {
        val binding =
            ExpenseDataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VendorExpensesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EmployeeExpensesAdapter.VendorExpensesViewHolder,
        position: Int
    ) {
        val current = list[position]
        holder.binding.expenseAmount.text = current.amount.toString()
        holder.binding.expenseToWhome.text = current.employee?.employeeName
        holder.binding.expenseDate.text = current.createdAt?.substring(0, 10)


    }

    fun updateList(newList: List<ExpenseData>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = list.size
            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition].id == newList[newItemPosition].id // Assuming EventData has an 'id' field
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition] == newList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class VendorExpensesViewHolder(val binding: ExpenseDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}