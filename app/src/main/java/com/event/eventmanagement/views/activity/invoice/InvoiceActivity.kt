package com.event.eventmanagement.views.activity.invoice

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.BuildConfig
import com.event.eventmanagement.databinding.ActivityInvoiceBinding
import com.event.eventmanagement.extras.AppUtils
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.createCustomerEvent.EventBodyRequest
import com.event.eventmanagement.views.activity.customerEventList.data.EventData
import com.event.eventmanagement.views.activity.invoice.data.PdfBody
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class InvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var preferenceManager: PreferenceManager
    private var ownerName: String? = null
    private val userViewModel:UserViewModel by viewModels()
    private val progressDialog by lazy { AppUtils.showProgressDialog(this) }
    private var fileName :String? = null
    private var token :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressDialog.show()


        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())

        preferenceManager = PreferenceManager(this)
        val userData = preferenceManager.getUserData()
        token = "Bearer ${preferenceManager.getToken()}"
        val paymentId = intent.getIntExtra("paymentId", 0)
        ownerName = userData?.ownerName
        val firstReceipt = intent.getBooleanExtra("firstReceipt", false)
        if (firstReceipt) {
            //first payment so no previous
            binding.previousLayout.visibility = View.GONE
            val invoiceData = intent.getParcelableExtra<EventBodyRequest>("invoiceData")
            val eventPackageName = intent.getStringExtra("PackageName")
            val invoiceNumber = intent.getIntExtra("invoiceNumber",0)
            binding.invoiceNumber.text = invoiceNumber.toString()
            binding.invoiceDate.text = currentDate
            binding.companyNameFrom.text = userData?.companyName

            //vendor details
            binding.companyName.text = userData?.companyName
            binding.vendorName.text = userData?.ownerName
            binding.vendorAddress.text = userData?.address
            binding.vendorMob.text = userData?.mobNo

            //customer details
            binding.customerName.text = invoiceData?.customerName
            binding.customerMob.text = invoiceData?.mobNo
            binding.customerAddress.text = invoiceData?.address

            //package details
            binding.eventPackageName.text = "${eventPackageName}\n(Rs. ${invoiceData?.amount})"
            if (invoiceData?.discount!! > 0) {
                binding.finalAmount.text =
                    "Rs. ${invoiceData?.finalAmount} \n (Discount: ${invoiceData?.discount})"
            } else {
                binding.finalAmount.text = "Rs. ${invoiceData?.finalAmount}"
            }
            binding.paidAmount.text = "Rs. ${invoiceData?.advanceAmount}"
            binding.remainingAmount.text = "Rs. ${invoiceData?.remainingAmount}"

            Handler(Looper.getMainLooper()).postDelayed({
                createPdfFirstPayment(invoiceData, paymentId)
            }, 2500)
        } else {
            //previous payment visibility
            val eventData = intent.getParcelableExtra<EventData>("event")
            val remainingAmount = intent.getStringExtra("remaining")
            val paidAmount = intent.getStringExtra("paidAmount")
            binding.previousLayout.visibility = View.VISIBLE

            binding.eventPackageName.text =
                "${eventData!!.eventPkg[0].packageName}\n(Rs. ${eventData!!.eventPkg[0].amount})"

            if (eventData.discount!! > 0) {
                binding.finalAmount.text =
                    "Rs. ${eventData?.finalAmount} \n (Discount: ${eventData?.discount})"
            } else {
                binding.finalAmount.text = "Rs. ${eventData?.finalAmount}"
            }
            binding.invoiceNumber.text = eventData.id.toString()
            binding.invoiceDate.text = currentDate
            //vendor details
            binding.companyName.text = userData?.companyName
            binding.vendorName.text = userData?.ownerName
            binding.vendorAddress.text = userData?.address
            binding.vendorMob.text = userData?.mobNo
            binding.companyNameFrom.text = userData?.companyName


            binding.customerName.text = eventData!!.customerdata[0].customerName
            binding.customerMob.text = eventData.customerdata[0].mobNo
            binding.customerAddress.text = eventData.customerdata[0].address

            val builder = StringBuilder()
            Log.d("event", eventData.eventPayment.toString())
            for (item in eventData.eventPayment) {
                builder.append("Rs. ${item.paidAmount} (${item.paidDate})").append("\n")
            }
            binding.previousAmount.text = builder.toString()

            binding.paidAmount.text = "Rs. ${paidAmount}"
            binding.remainingAmount.text = "Rs. ${remainingAmount}"

            Handler(Looper.getMainLooper()).postDelayed({
                createPdfPayment(eventData, paymentId)
            }, 800)
        }

        binding.share.setOnClickListener {
            val pdfFile = getPdfFile(fileName!!)
            sharePdf(pdfFile)
        }

        userViewModel.error.observe(this){
            progressDialog.dismiss()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 200)
        }
        userViewModel.paymentUrlResponse.observe(this) { response ->
            if (response != null) {
                progressDialog.dismiss()
            }
        }
    }


    private fun getPdfFile(fileName:String): File {
        return File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
    }

    private fun createPdfFirstPayment(invoiceData: EventBodyRequest, paymentId: Int) {
        // Inflate the custom view
//        val inflater = LayoutInflater.from(this)
//        val customView = inflater.inflate(R.layout.activity_invoice, null)

        // Measure and layout the view
        val sdf = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
        val currentDateAndTime = sdf.format(Date())
        val displayMetrics = resources.displayMetrics
        binding.main.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        binding.main.layout(0, 0, binding.main.measuredWidth, binding.main.measuredHeight)

        // Create the PDF document
        val document = PdfDocument()
        val pageWidth =  binding.main.measuredWidth // A4 width in points
        val pageHeight = binding.main.measuredHeight // A4 height in points
        val margin = 20 // Margin in points
        val totalHeight = binding.main.height

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "${invoiceData.customerName}_invoice_${currentDateAndTime}.pdf"
        )

        fileName = file.name

        try {
            val fileOutputStream = FileOutputStream(file, false)
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            // Translate and scale the canvas to fit the view
            val scale = (pageWidth - 2 * margin).toFloat() / binding.main.measuredWidth
            canvas.translate(margin.toFloat(), margin.toFloat())
            canvas.scale(scale, scale)

            // Draw the view on the canvas
            binding.main.draw(canvas)

            document.finishPage(page)

            document.writeTo(fileOutputStream)
            fileOutputStream.close()
            document.close()
            Log.d("PDF", "PDF created successfully: ${file.absolutePath}")

            uploadPdfToFirebase(file, paymentId)
        } catch (e: IOException) {
            Log.e("PDF", "Error creating PDF", e)
        }
    }

    private fun createPdfPayment(eventData: EventData, paymentId: Int) {
        // Inflate the custom view
//        val inflater = LayoutInflater.from(this)
//        val customView = inflater.inflate(R.layout.activity_invoice, null)
        val sdf = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
        val currentDateAndTime = sdf.format(Date())
        // Measure and layout the view
        val displayMetrics = resources.displayMetrics
        binding.main.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        binding.main.layout(0, 0, binding.main.measuredWidth, binding.main.measuredHeight)
        // Create the PDF document
        val document = PdfDocument()
        val pageWidth = binding.main.measuredWidth // A4 width in points
        val pageHeight = binding.main.measuredHeight // A4 height in points
        val margin = 20 // Margin in points
        val totalHeight = binding.main.height

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "${eventData.customerdata[0].customerName}_invoice_${currentDateAndTime}.pdf"
        )

        fileName = file.name

        try {
            val fileOutputStream = FileOutputStream(file, false)
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            // Translate and scale the canvas to fit the view
            val scale = (pageWidth - 2 * margin).toFloat() / binding.main.measuredWidth
            canvas.translate(margin.toFloat(), margin.toFloat())
            canvas.scale(scale, scale)

            // Draw the view on the canvas
            binding.main.draw(canvas)

            document.finishPage(page)

            document.writeTo(fileOutputStream)
            fileOutputStream.close()
            document.close()

            Log.d("PDF", "PDF created successfully: ${file.absolutePath}")

            uploadPdfToFirebase(file, paymentId)
        } catch (e: IOException) {
            Log.e("PDF", "Error creating PDF", e)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission denied, handle accordingly
            }
        }
    }

    private fun sharePdf(pdfFile: File) {
        try {
            val uri = FileProvider.getUriForFile(
                this,
                "${BuildConfig.APPLICATION_ID}.fileprovider",
                pdfFile
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(intent, "Share PDF via"))
        } catch (e: Exception) {
            Log.e("Share PDF", "Error sharing PDF", e)
        }
    }

    private fun uploadPdfToFirebase(pdfFile: File, paymentId: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val storageRef = FirebaseStorage.getInstance().reference
        val pdfRef =
            storageRef.child("invoices").child(ownerName!!).child(currentDate).child(pdfFile.name)

        val uploadTask = pdfRef.putFile(Uri.fromFile(pdfFile))

        uploadTask.addOnSuccessListener {
            // File uploaded successfully
            Log.d("PDF", "PDF uploaded to Firebase Storage")

            // Optionally, you can get the download URL of the uploaded file
            pdfRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                Log.d("PDF", "Download URL: $downloadUrl")

                // Now you can use the download URL for further access

                //call api to save the url

                val pdfBody = PdfBody(
                    paymentId.toString(), downloadUrl,pdfFile.name
                )
                userViewModel.updatePdfUrl(token,pdfBody)


            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful uploads
            Log.e("PDF", "Error uploading PDF to Firebase Storage", exception)
            finish()
        }
    }

    companion object {
        private const val REQUEST_WRITE_PERMISSION = 100
    }
}