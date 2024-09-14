package com.event.eventmanagement.views.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.databinding.EmployeeItemBinding
import com.event.eventmanagement.views.activity.addEmployee.EmployeeBody

class EmployeeAdapter() : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    private val list: ArrayList<EmployeeBody> = ArrayList()

    inner class EmployeeViewHolder(val binding: EmployeeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<EmployeeBody>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val current = list[position]
        holder.binding.employeeName.text = current.employeeName
        holder.binding.employeeAddress.text =
            "${current.address},${current.cityId},${current.stateId},${current.countryId},${current.pincode}"
        holder.binding.employeeMobileNumber.text = current.mobNo
        holder.binding.employeeAlternateMobileNo.text = current.altMobNo
        holder.binding.employeeDob.text = current.dob
        holder.binding.employeeBankName.text = current.bankName
        holder.binding.employeeAccountNumber.text = current.accountNo
        holder.binding.employeeBankIfsc.text = current.ifscCode
        holder.binding.employeeSalaryType.text = current.salaryType
        holder.binding.employeeSalaryAmount.text = "₹${current.salaryAmount}"
        holder.binding.employeeAdhharNumber.text = current.adharNo

        holder.binding.showMore.setOnClickListener {
            if (holder.binding.additionalDetails.visibility == View.VISIBLE) {
                holder.binding.additionalDetails.visibility = View.GONE
            } else {
                holder.binding.additionalDetails.visibility = View.VISIBLE
            }
        }

    }

}