package com.madan.bookhotel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.*
//import com.aakash.travelnepal.db.DestinationDB
import com.madan.bookhotel.entity.User
import com.madan.bookhotel.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity(){
    private lateinit var ivProfile : ImageView
    private  lateinit var etFirstName: EditText
    private lateinit var etLastName : EditText
    private lateinit var etAddress : EditText
    private lateinit var etEmailAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etUsername : EditText
    private lateinit var etPassword : EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var tvSignUp : TextView

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        ivProfile = findViewById(R.id.imggg)
        etFirstName = findViewById(R.id.etFirstname)
        etLastName = findViewById(R.id.etLastname)
        etAddress = findViewById(R.id.etAddress)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        tvSignUp = findViewById(R.id.tvSignUp)

        tvSignUp.setOnClickListener{
            val firstname = etFirstName.text.toString()
            val lastname = etLastName.text.toString()
            val address = etAddress.text.toString()
            val email = etEmailAddress.text.toString()
            val phone = etPhoneNumber.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password!= confirmPassword){
                etPassword.error = "Password does not match"
                etPassword.requestFocus()
                return@setOnClickListener
            }else{
                val user = User(firstname = firstname, lastname = lastname, address = address, email = email, phone = phone, username = username, password = password )

                //APi code goes here
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userRepository = UserRepository()
                        val response = userRepository.registerUser(user)

                        if (response.success == true){

                            if (imageUrl != null){
                                uploadImage(response.data!!._id)
                            }
                            withContext(Main){

                                Toast.makeText(this@SignUpActivity, "User Registered", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
                            }
                        }
                    }
                    catch (ex: Exception) {
                        withContext(Main){
                            Toast.makeText(this@SignUpActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                            println(ex.printStackTrace())
                        }
                    }
                }
            }
        }
        ivProfile.setOnClickListener {
            lodPopUp()
        }
    }

    private fun lodPopUp() {
        val popupMenu = PopupMenu(this@SignUpActivity, ivProfile)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun uploadImage(_id: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)

            val extensions = MimeTypeMap.getFileExtensionFromUrl(imageUrl)
            val mimetype : String? = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extensions)

            val reqFile =
                    RequestBody.create(MediaType.parse(mimetype), file)

            val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepository = UserRepository()
                    val response = userRepository.uploadImage(_id, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, "Image Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Error ", ex.localizedMessage)
                        Toast.makeText(
                                this@SignUpActivity,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                //overall location of selected image
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                //locator and identifier
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()//moveTONext // movetolast
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                //image preview
                ivProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                ivProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }
    private fun bitmapToFile(imageBitmap: Bitmap, s: String): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + s
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

}