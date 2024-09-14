package com.event.eventmanagement.views.activity.gallery

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.event.eventmanagement.databinding.ActivityAddGalleryImagesBinding
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.usersession.PreferenceManager
import com.event.eventmanagement.views.activity.gallery.adapter.ImageAdapter
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddGalleryImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGalleryImagesBinding
    private var vendorId: String? = null
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter: ImageAdapter
    private lateinit var preferenceManager: PreferenceManager
    val map: HashMap<String, Int> = HashMap()
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityAddGalleryImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedImages.clear()

        preferenceManager = PreferenceManager(this)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding.uploadImages.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .createIntent {
                    startForProfileImageResult.launch("image/*")
                }


        }


        vendorId = preferenceManager.getVendorId().toString()
        token = "Bearer ${preferenceManager.getToken()}"
        userViewModel.getEvents(token!!,vendorId!!)

        val listEvent = ArrayList<String>()
        userViewModel.getAllEventsResponse.observe(this) { result ->
            listEvent.clear()
            map.clear()
            if (result != null) {
                val response = result
                val list = response.data
                for (item in list) {
                    listEvent.add(item.eventName!!)
                    map[item.eventName!!.trim()] = item.id!!
                }

                val adapter = ArrayAdapter<String>(
                    this@AddGalleryImagesActivity,
                    android.R.layout.simple_dropdown_item_1line, listEvent
                )
                binding.selectServiceType.setAdapter(adapter)
            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }



        binding.save.setOnClickListener {
            if (binding.selectServiceType.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select service type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (selectedImages.isEmpty()) {
                Toast.makeText(this, "Please select images", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                val filesDir = applicationContext.filesDir
                val file = File(filesDir, "image${System.currentTimeMillis()}.png")
                val imagesMultiPart :ArrayList<MultipartBody.Part> = ArrayList()
//                if (selectedImages.size == 1) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    val requestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                    partTwo = MultipartBody.Part.createFormData("image_two", file.name, requestBody)
//                    partThree =
//                        MultipartBody.Part.createFormData("image_three", file.name, requestBody)
//                    partFour =
//                        MultipartBody.Part.createFormData("image_four", file.name, requestBody)
//                    partFive =
//                        MultipartBody.Part.createFormData("image_five", file.name, requestBody)
//                    partSix = MultipartBody.Part.createFormData("image_six", file.name, requestBody)
//                    partSeven =
//                        MultipartBody.Part.createFormData("image_seven", file.name, requestBody)
//                } else if (selectedImages.size == 2) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    partTwo = getMultipartBody(selectedImages.getOrNull(1)!!, 1)
//                    val requestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                    partThree =
//                        MultipartBody.Part.createFormData("image_three", file.name, requestBody)
//                    partFour =
//                        MultipartBody.Part.createFormData("image_four", file.name, requestBody)
//                    partFive =
//                        MultipartBody.Part.createFormData("image_five", file.name, requestBody)
//                    partSix = MultipartBody.Part.createFormData("image_six", file.name, requestBody)
//                    partSeven =
//                        MultipartBody.Part.createFormData("image_seven", file.name, requestBody)
//                } else if (selectedImages.size == 3) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    partTwo = getMultipartBody(selectedImages.getOrNull(1)!!, 1)
//                    partThree = getMultipartBody(selectedImages.getOrNull(2)!!, 2)
//                    val requestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                    partFour =
//                        MultipartBody.Part.createFormData("image_four", file.name, requestBody)
//                    partFive =
//                        MultipartBody.Part.createFormData("image_five", file.name, requestBody)
//                    partSix = MultipartBody.Part.createFormData("image_six", file.name, requestBody)
//                    partSeven =
//                        MultipartBody.Part.createFormData("image_seven", file.name, requestBody)
//                } else if (selectedImages.size == 4) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    partTwo = getMultipartBody(selectedImages.getOrNull(1)!!, 1)
//                    partThree = getMultipartBody(selectedImages.getOrNull(2)!!, 2)
//                    partFour = getMultipartBody(selectedImages.getOrNull(3)!!, 3)
//                    val requestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//
//                    partFive =
//                        MultipartBody.Part.createFormData("image_five", file.name, requestBody)
//                    partSix = MultipartBody.Part.createFormData("image_six", file.name, requestBody)
//                    partSeven =
//                        MultipartBody.Part.createFormData("image_seven", file.name, requestBody)
//                } else if (selectedImages.size == 5) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    partTwo = getMultipartBody(selectedImages.getOrNull(1)!!, 1)
//                    partThree = getMultipartBody(selectedImages.getOrNull(2)!!, 2)
//                    partFour = getMultipartBody(selectedImages.getOrNull(3)!!, 3)
//                    partFive = getMultipartBody(selectedImages.getOrNull(4)!!, 4)
//                    val requestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                    partSix = MultipartBody.Part.createFormData("image_six", file.name, requestBody)
//                    partSeven =
//                        MultipartBody.Part.createFormData("image_seven", file.name, requestBody)
//                } else if (selectedImages.size == 6) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    partTwo = getMultipartBody(selectedImages.getOrNull(1)!!, 1)
//                    partThree = getMultipartBody(selectedImages.getOrNull(2)!!, 2)
//                    partFour = getMultipartBody(selectedImages.getOrNull(3)!!, 3)
//                    partFive = getMultipartBody(selectedImages.getOrNull(4)!!, 4)
//                    partSix = getMultipartBody(selectedImages.getOrNull(5)!!, 5)
//                    val requestBody = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                    partSeven =
//                        MultipartBody.Part.createFormData("image_seven", file.name, requestBody)
//                } else if (selectedImages.size == 7) {
//                    partOne = getMultipartBody(selectedImages.getOrNull(0)!!, 0)
//                    partTwo = getMultipartBody(selectedImages.getOrNull(1)!!, 1)
//                    partThree = getMultipartBody(selectedImages.getOrNull(2)!!, 2)
//                    partFour = getMultipartBody(selectedImages.getOrNull(3)!!, 3)
//                    partFive = getMultipartBody(selectedImages.getOrNull(4)!!, 4)
//                    partSix = getMultipartBody(selectedImages.getOrNull(5)!!, 5)
//                    partSeven = getMultipartBody(selectedImages.getOrNull(6)!!, 6)
//                }

                runBlocking {
                    for (item in selectedImages) {
                        val part = getMultipartBody(item)
                        imagesMultiPart.add(part)
                    }
                }
//
                val vendorId = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    preferenceManager.getVendorId().toString()
                )
                var eventId = 0
                for (key in map.keys){
                    if (key == binding.selectServiceType.text.toString()){
                        eventId = map[key]!!
                    }
                }
                val eventIdParam = RequestBody.create("text/plain".toMediaTypeOrNull(),
                    eventId.toString())


                userViewModel.uploadImages(token!!,
                    eventIdParam,
                    vendorId,imagesMultiPart
                )

            }
        }



        userViewModel.getImageResponse.observe(this) { result ->
            if (result != null) {
                finish()
            }
        }



        adapter = ImageAdapter()
        binding.imageRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.imageRecyclerView.adapter = adapter


    }

    private var selectedImages = ArrayList<Uri>()
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri ->
            if (uri.isNotEmpty()) {
                for (item in uri) {
                    if (!selectedImages.contains(item) && selectedImages.size < 7) {
                        selectedImages.add(item)
                    } else {
                        Toast.makeText(this, "Image already selected", Toast.LENGTH_SHORT).show()
                    }
                }

                adapter.setImages(uri)
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun getMultipartBody(uri: Uri): MultipartBody.Part {
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image${System.currentTimeMillis()}.png")
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(
            "images",
            file.name,
            requestBody
        )
    }
}