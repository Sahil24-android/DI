package com.event.eventmanagement.views.auth

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.event.eventmanagement.databinding.ActivityRegistrationBinding
import com.event.eventmanagement.extras.CustomProgressDialog
import com.event.eventmanagement.model.UserViewModel
import com.event.eventmanagement.views.auth.datasource.RegistrationDataSend
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var userViewModel: UserViewModel
    private val loader by lazy { CustomProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]


        progressDialog = ProgressDialog(this)

        userViewModel.getServices()


        userViewModel.error.observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }

        userViewModel.isLoading.observe(this) {
            if (it) {
                loader.show()
            } else {
                loader.dismiss()
            }
        }

        val hasMap: HashMap<String, Int> = HashMap()
        val list = ArrayList<String>()

        userViewModel.serviceName.observe(this) { result ->
            if (result != null) {
                list.clear()
                for (item in result.data) {
                    list.add(item.serviceName!!)
                    hasMap.put(item.serviceName!!, item.id!!)
                }
                val adapter = ArrayAdapter<String>(
                    this@RegistrationActivity,
                    android.R.layout.simple_dropdown_item_1line, list
                )
                binding.selectServiceType.setAdapter(adapter)
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }


        }

        binding.selectServiceType.setOnItemClickListener { parent, view, position, id ->
            val selectedString = list[position]
            if (selectedString == "Select") {
            } else {
                val getId = hasMap[selectedString]
                serviceId = getId
                userViewModel.getServicePackage(getId.toString())
            }
        }

        val packageList = ArrayList<String>()
        val packageMap = HashMap<String, Int>()
        binding.selectServicePackage.setOnItemClickListener { parent, view, position, id ->
            val selectedString = packageList[position]
            if (selectedString == "Select") {
            } else {
                val getId = packageMap[selectedString]
                servicePackageId = getId
            }
        }

        userViewModel.servicePackages.observe(this) { result ->
            if (result != null) {
                packageList.clear()
                if (result.data.isEmpty()) {
                    Toast.makeText(this, "No Package Found", Toast.LENGTH_SHORT).show()
                    binding.selectServicePackage.setAdapter(null)
                    binding.selectServicePackage.setText("")
                    return@observe
                } else {
                    packageList.clear()
                    for (item in result.data) {
                        packageList.add(item.packageName!!)
                        packageMap.put(item.packageName!!, item.id!!)
                    }
                    val adapterPackage = ArrayAdapter<String>(
                        this@RegistrationActivity,
                        android.R.layout.simple_dropdown_item_1line, packageList
                    )
                    binding.selectServicePackage.setAdapter(adapterPackage)

                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }



        binding.uploadLogo.setOnClickListener {
            progressDialog.show()
            ImagePicker.with(this)
                .compress(1024)
                .crop()//Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }


        binding.back.setOnClickListener {
            finish()
        }

        binding.selectServicePackage.setOnClickListener {
            if (packageList.isEmpty()) {
                Toast.makeText(this, "No Package Available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.register.setOnClickListener {
            if (fieldValidation()) {
                if (binding.password.text.toString() == binding.confirmPassword.text.toString()) {
                    val filesDir = applicationContext.filesDir
                    val file = File(filesDir, "image.png")
                    val part: MultipartBody.Part
                    if (imageUri != null) {
                        val inputStream = contentResolver.openInputStream(imageUri!!)
                        val outputStream = FileOutputStream(file)
                        inputStream!!.copyTo(outputStream)
                        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                        part =
                            MultipartBody.Part.createFormData("logo_image", file.name, requestBody)
                    } else {
                        val requestBody =
                            "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                        part = MultipartBody.Part.createFormData("logo_image", "", requestBody)
                    }
                    val data = RegistrationDataSend(
                        binding.companyName.text.toString(),
                        binding.ownerName.text.toString(),
                        binding.mobileNumber.text.toString(),
                        if (binding.alternateMobileNumber.text.toString().isEmpty()) {
                            binding.mobileNumber.text.toString()
                        } else {
                            binding.alternateMobileNumber.text.toString()
                        },
                        binding.address.text.toString(),
                        binding.selectPinCode.text.toString(),
                        binding.city.text.toString(),
                        binding.state.text.toString(),
                        binding.country.text.toString(),
                        serviceId.toString(),
                        servicePackageId.toString(),
                        binding.confirmPassword.text.toString(),
                    )
                    userViewModel.registerUser(data, part)
                } else {
                    Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(it,"All Fields Are Mandatory",Snackbar.LENGTH_SHORT).show()
            }

        }


        userViewModel.userRegister.observe(this) { result ->
            if (result != null) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }



        binding.selectPinCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 6) {
                    userViewModel.getLocationsData(s.toString())
                } else {
                    binding.state.text = null
                    binding.city.text = null
                    binding.country.text = null
                }
            }

        })

        userViewModel.locationData.observe(this) { result ->
            if (result != null) {
                val getFirstData = result.get(0)
                if (getFirstData.PostOffice.isNotEmpty()) {
                    try {
                        val data = getFirstData.PostOffice.get(0)
                        binding.state.text = data.State
                        binding.city.text = data.Region
                        binding.country.text = data.Country
                    }catch (e :Exception){
                        binding.state.text = null
                        binding.city.text = null
                        binding.country.text = null
                        binding.selectPinCode.text = null
                        Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.state.text = null
                binding.city.text = null
                binding.country.text = null
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun createMultipartFormData(
        contentResolver: ContentResolver,
        imageUri: Uri?
    ): MultipartBody.Part? {
        var imagePart: MultipartBody.Part? = null

        // If imageUri is provided, add image file to multipart form data
        if (imageUri != null) {
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
            if (inputStream != null) {
                val file = createTempFile(inputStream)
                if (file != null) {
                    val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    imagePart = MultipartBody.Part.createFormData("image", file.name, requestBody)
                }
            }
        }

        return imagePart
    }

    var serviceId: Int? = null
    var servicePackageId: Int? = null

    @Throws(IOException::class)
    fun createTempFile(inputStream: InputStream?): File? {
        inputStream ?: return null

        val file = File.createTempFile("image", null, Environment.getExternalStorageDirectory())
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()

        return file
    }

    fun fieldValidation(): Boolean {
        return (binding.companyName.text.toString().isEmpty() ||
                (binding.ownerName.text.toString().isEmpty()) ||
                (binding.mobileNumber.text.toString().isEmpty()) ||
                (binding.address.text.toString().isEmpty()) || (binding.city.text.toString()
            .isEmpty())
                || (binding.selectPinCode.text.toString()
            .isEmpty()) || (binding.country.text.toString().isEmpty())
                || (binding.selectServiceType.text.toString()
            .isEmpty()) || (binding.selectServicePackage.text.toString().isEmpty()))
    }

    private var imageUri: Uri? = null
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                imageUri = data?.data!!

                //mProfileUri = fileUri
                binding.brandLogo.setImageURI(imageUri)
                progressDialog.dismiss()

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()

            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()

            }
        }

}