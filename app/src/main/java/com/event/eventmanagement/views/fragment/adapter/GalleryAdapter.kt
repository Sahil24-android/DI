package com.event.eventmanagement.views.fragment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.apis.RetrofitClient
import com.event.eventmanagement.databinding.GalleryItemBinding
import com.event.eventmanagement.views.activity.image.FullScreenZoomActivity
import com.event.eventmanagement.views.fragment.datasource.GalleryItem
import com.squareup.picasso.Picasso

class GalleryAdapter() : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private val list: ArrayList<GalleryItem> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryAdapter.GalleryViewHolder {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryAdapter.GalleryViewHolder, position: Int) {
        val item = list[position]
        Picasso.get().load(
            RetrofitClient.BASE_URL.replace(
                "api/",""
            )+"images/gallery/"+ item.images
        ).into(holder.binding.galleryImage)

        holder.binding.galleryImage.setOnClickListener {
            val intent = Intent(holder.itemView.context, FullScreenZoomActivity::class.java)
            intent.putExtra("imageUrl", item.images)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun updateList(newList: ArrayList<GalleryItem>){
        list.clear()
        list.addAll(newList.reversed())
        notifyDataSetChanged()
    }

    inner class GalleryViewHolder(val binding: GalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}