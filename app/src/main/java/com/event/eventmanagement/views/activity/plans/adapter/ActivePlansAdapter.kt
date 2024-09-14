package com.event.eventmanagement.views.activity.plans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.databinding.ActivePlanItemBinding
import com.event.eventmanagement.views.auth.datasource.PackageItem

class ActivePlansAdapter(private val listener:OnItemClickListener) : RecyclerView.Adapter<ActivePlansAdapter.ActivePlansViewHolder>() {

    private val list: ArrayList<PackageItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivePlansViewHolder {
        val binding =
            ActivePlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivePlansViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: ArrayList<PackageItem>) {
        list.clear()
        notifyDataSetChanged()
        list.addAll(newList)
    }

    override fun onBindViewHolder(holder: ActivePlansViewHolder, position: Int) {
        val current = list[position]

        holder.binding.apply {
            planType.text = current.packageName
//            description.text = current.description
            amount.text = "\u20B9 ${current.amount}"
            timeDuration.text = "for ${current.validityInDays} Days"
        }

    }


    inner class ActivePlansViewHolder(val binding: ActivePlanItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.proceedToPay.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val current = list[position]
                        listener.onItemClick(position,current)
                    }
                }
            }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int,item:PackageItem)
    }
}