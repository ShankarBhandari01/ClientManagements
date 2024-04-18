package com.shankar.clientmanagements.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.repository.ClientsRepository
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
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var tvProximity: TextView
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null


    private lateinit var tv_name: TextView
    private lateinit var gender: TextView
    private lateinit var ProfilePIc: ImageView
    private lateinit var tv_address: TextView
    private lateinit var contact: TextView
    private lateinit var tv_username: TextView
    private lateinit var citizenshipNumber: TextView
    private lateinit var tvDOb: TextView
    private lateinit var Edit_img: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(R.layout.fragment_profile, container, false)

        tv_name = View.findViewById(R.id.tv_name)
        gender = View.findViewById(R.id.gender)
        contact = View.findViewById(R.id.contact)
        citizenshipNumber = View.findViewById(R.id.citizenshipNumber)
        tvDOb = View.findViewById(R.id.tvDOb)
        ProfilePIc = View.findViewById(R.id.ProfilePIc)
        tv_address = View.findViewById(R.id.tv_address)
        tv_username = View.findViewById(R.id.tv_username)
        Edit_img = View.findViewById(R.id.Edit_img)
        loadProfile()
        Edit_img.setOnClickListener {
            Editprofile()
        }

        return View
    }

    fun loadProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val user =
                context?.let { RoomDataBase.getInstance(it).getUserResponseDAO().getClients() }
            withContext(Main) {
                tv_name.text = user!![0].full_name.toString()
                gender.text = user!![0].gender.toString()
                contact.text = user!![0].contact.toString()
                tv_address.text = user[0].address.toString()
                citizenshipNumber.text = user[0].citizenshipNumber.toString()
                tv_username.text = user[0].username.toString()
                tvDOb.text = user[0].Dob.toString()
                val imgePath = ServiceBuilder.loadImagePath() + user[0].image!!.split("\\")[0]
                Glide.with(this@ProfileFragment)
                    .load(imgePath)
                    .fitCenter()
                    .into(ProfilePIc)
            }


        }
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = activity?.contentResolver;
                val cursor =
                    contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                ProfilePIc.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
                uploadImage()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                ProfilePIc.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                uploadImage()
            }
        }
    }

    private fun Editprofile() {
        val popupMenu = PopupMenu(context, ProfilePIc)
        popupMenu.menuInflater.inflate(R.menu.gallarymenu, popupMenu.menu)
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

    private fun uploadImage() {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("image/jpg"), file)
            val body =
                MultipartBody.Part.createFormData("image", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val clientsRepository = ClientsRepository()
                    val response = clientsRepository.uploadImage(body)
                    if (response.status == true) {
                        withContext(Main) {
                            Toast.makeText(context, "${response.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(context, "${response.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Main) {
                        Log.d("Mero Error ", ex.localizedMessage)
                        Toast.makeText(
                            context,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file?.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
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


