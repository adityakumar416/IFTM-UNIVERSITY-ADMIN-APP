package com.example.iftmadmin

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.iftmadmin.databinding.FragmentAddBannerBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException

class AddBannerFragment : Fragment() {
    private lateinit var binding: FragmentAddBannerBinding
    private var firebaseStorage: FirebaseStorage? = null
    private var firebaseDatabase: FirebaseDatabase? = null

    private lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBannerBinding.inflate(layoutInflater, container, false)

        // on below line adding click listener for our choose image button.
        val firebaseStorage = FirebaseStorage.getInstance().getReference("banners")
        val databaseRef = FirebaseDatabase.getInstance().getReference("banners")

        binding.buttonChooseImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 71)


        }




        binding.buttonUpload.setOnClickListener(object : android.view.View.OnClickListener {

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onClick(v: android.view.View?) {
                val processDialog = ProgressDialog(context)
                processDialog.setMessage("Image Uploading")
                processDialog.setCancelable(false)
                processDialog.show()


                val storageRef = firebaseStorage.child(System.currentTimeMillis().toString()+"."+ getFileExtension(uri))
                storageRef.putFile(uri)
                    .addOnSuccessListener {

                        Log.i(TAG, "onSuccess Main: $it")

                        Toast.makeText(context, "Upload Image Successfully", Toast.LENGTH_SHORT).show()
                        processDialog.dismiss()


                        val urlTask: Task<Uri> = it.storage.downloadUrl
                        while (!urlTask.isSuccessful);
                        val downloadUrl:Uri = urlTask.result
                        Log.i(TAG, "onSuccess: $downloadUrl")

                        val imageModel =ImageModel(databaseRef.push().key,downloadUrl.toString())
                        val uploadId =imageModel.imageId

                        if (uploadId != null) {
                            databaseRef.child(uploadId).setValue(imageModel)
                        }


                    }

                    .addOnFailureListener {

                        Toast.makeText(context, "Failed to Upload Image", Toast.LENGTH_SHORT).show()
                        processDialog.dismiss()

                    }
                    .addOnProgressListener { taskSnapshot -> //displaying the upload progress
                        val progress =
                            100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        processDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    }
            }
        })
        return binding.root
    }


    private fun getFileExtension(uri: Uri): String? {
        val cR: ContentResolver = requireContext().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 71 && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            uri = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                binding.imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}