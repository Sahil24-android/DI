package com.event.eventmanagement.views.fragment.adapter

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.event.eventmanagement.R
import com.event.eventmanagement.views.activity.customerEventList.data.EventPayment
import com.event.eventmanagement.views.activity.customerEventList.data.ExpensePayment
import com.google.android.material.textview.MaterialTextView

class ExposedPaymentAdapter(private val context: Context) : RecyclerView.Adapter<ExposedPaymentAdapter.PaymentsViewHolder>() {

    private var paymentsList = ArrayList<ExpensePayment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExposedPaymentAdapter.PaymentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_item, parent, false)
        createNotificationChannel()
        return PaymentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExposedPaymentAdapter.PaymentsViewHolder, position: Int) {
        val current = paymentsList[position]
        holder.sequence.text = (position+1).toString()
        holder.paymentDate.text = current.createdAt?.substring(0,10)
        holder.paymentAmount.text = current.amount.toString()

        holder.downloadPdf.visibility = View.INVISIBLE
//        holder.downloadPdf.setOnClickListener {
//            startDownload(current.pdfUrl!!,current.pdfName!!)
//        }
    }

    override fun getItemCount(): Int {
       return paymentsList.size
    }

    fun updateList(newList: ArrayList<ExpensePayment>) {
        paymentsList.clear()
        paymentsList.addAll(newList)
        notifyDataSetChanged()
    }


    inner class PaymentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val paymentDate:MaterialTextView = itemView.findViewById(R.id.paymentDate)
        val sequence:MaterialTextView = itemView.findViewById(R.id.sequence)
        val paymentAmount:MaterialTextView = itemView.findViewById(R.id.paymentAmount)
        val downloadPdf:ImageView = itemView.findViewById(R.id.pdfDownload)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "download_channel",
                "Download Notifications",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Channel for download notifications"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private lateinit var downloadManager: DownloadManager
    private var downloadID: Long = 0
    private fun startDownload(url: String,fileName:String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading PDF")
            .setDescription("Please wait...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)

        // Register receiver to track download completion
        context.registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),)
    }
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadID) {
                val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val builder = NotificationCompat.Builder(context, "download_channel")
                    .setContentTitle("Download Complete")
                    .setContentText("The PDF has been downloaded successfully.")
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                val notificationIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                builder.setContentIntent(pendingIntent)
                notificationManager.notify(1, builder.build())

                context.unregisterReceiver(this)
            }
        }
    }



}