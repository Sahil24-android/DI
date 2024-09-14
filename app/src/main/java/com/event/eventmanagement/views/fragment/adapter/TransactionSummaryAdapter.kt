package com.event.eventmanagement.views.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.views.fragment.datasource.ReportData
import com.google.android.material.textview.MaterialTextView

class TransactionSummaryAdapter(private val context: Context):RecyclerView.Adapter<TransactionSummaryAdapter.TransactionSummaryViewHolder>() {

    private val list= ArrayList<ReportData>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionSummaryAdapter.TransactionSummaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_data_item,parent,false)
        return TransactionSummaryViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TransactionSummaryAdapter.TransactionSummaryViewHolder,
        position: Int
    ) {
        val current  = list[position]
        holder.expenseName.text=current.name

        holder.expenseDate.text=current.date?.substring(0,10)
        if (current.expenseOrIncome){
            holder.expenseOrGain.text="Income"
            holder.expenseOrGain.setTextColor(context.resources.getColor(R.color.green_pays))
            holder.expenseAmount.text="+ \u20b9 ${current.amount}"
            holder.expenseAmount.setTextColor(context.resources.getColor(R.color.green_pays))

        }else{
            holder.expenseOrGain.text="Expense"
            holder.expenseOrGain.setTextColor(context.resources.getColor(R.color.error_red))
            holder.expenseAmount.text="- \u20b9 ${current.amount}"
            holder.expenseAmount.setTextColor(context.resources.getColor(R.color.error_red))


        }
    }

    fun updateList(newList:List<ReportData>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class TransactionSummaryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val expenseName=itemView.findViewById<MaterialTextView>(R.id.expenseToWhome)
        val expenseAmount=itemView.findViewById<MaterialTextView>(R.id.expenseAmount)
        val expenseDate=itemView.findViewById<MaterialTextView>(R.id.expenseDate)
        val expenseOrGain = itemView.findViewById<MaterialTextView>(R.id.expenseOrGain)
    }

}