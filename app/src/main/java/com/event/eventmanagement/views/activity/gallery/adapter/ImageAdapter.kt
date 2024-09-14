package com.event.eventmanagement.views.activity.gallery.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.squareup.picasso.Picasso

class ImageAdapter():RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val images: ArrayList<Uri> = ArrayList()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagePath = images[position]

        holder.imageView.setImageURI(imagePath)
        //Picasso.get().load(imagePath).into(holder.imageView)
       // Glide.with(holder.itemView.context).load(imagePath).into(holder.imageView)
    }

    fun setImages(newList: List<Uri>) {
        images.clear()
        images.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return images.size
    }
}