package com.event.eventmanagement.views.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.event.eventmanagement.R
import com.event.eventmanagement.views.fragment.datasource.ExpenseData

class VendorExpensesAdapter():RecyclerView.Adapter<VendorExpensesAdapter.VendorExpensesViewHolder>() {

    private val list:ArrayList<ExpenseData> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VendorExpensesAdapter.VendorExpensesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_data_item,parent,false)
        return VendorExpensesViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: VendorExpensesAdapter.VendorExpensesViewHolder,
        position: Int
    ) {

    }

    override fun getItemCount(): Int {
       return list.size
    }

    inner class VendorExpensesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

}