package com.example.iftmadmin.fragment.courses

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.iftmadmin.databinding.FragmentAddCoursesBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException

class AddCoursesFragment : Fragment() {
    private lateinit var binding: FragmentAddCoursesBinding
    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCoursesBinding.inflate(layoutInflater, container, false)



        binding.btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 71)
        }

        binding.addCourse.setOnClickListener {

            val courseName =binding.courseName.text.toString()
            val courseDuration =binding.courseDuration.text.toString()
            val courseFees =binding.courseFees.text.toString()

            if(courseName.isEmpty()||courseDuration.isEmpty()||courseFees.isEmpty()){
                Toast.makeText(
                    context,
                    "Please enter all fields.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {

                addDataToFirebase(courseName, courseDuration, courseFees)
            }



        }


        return binding.root
    }

    private fun addDataToFirebase(courseName: String, courseDuration: String, courseFees: String) {
        val processDialog = ProgressDialog(context)
        processDialog.setMessage("Image Uploading")
        processDialog.setCancelable(false)
        processDialog.show()

        val firebaseStorage = FirebaseStorage.getInstance().getReference("courses")
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("courses")

        val storageRef = firebaseStorage.child(System.currentTimeMillis().toString()+"."+ getFileExtension(uri))
        storageRef.putFile(uri)
            .addOnSuccessListener { takeSnapshot ->
                Log.i(ContentValues.TAG, "onSuccess Main: $takeSnapshot")

                Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show()
                processDialog.dismiss()

                val urlTask: Task<Uri> = takeSnapshot!!.storage.downloadUrl
                while (!urlTask.isSuccessful);
                val downloadUrl: Uri = urlTask.result
                Log.i(ContentValues.TAG, "onSuccess: $downloadUrl")

                val courseModel = CourseModel(
                    firebaseDatabase!!.push().key,
                    downloadUrl.toString(),
                    courseName,
                    courseDuration,
                    courseFees
                )
                val uploadId = courseModel.courseId

                firebaseDatabase!!.child(uploadId.toString()).setValue(courseModel)

                binding.courseName.text = null
                binding.courseDuration.text = null
                binding.courseFees.text = null
                binding.btnImage.setImageBitmap(null)
            }

            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to Upload Image",
                    Toast.LENGTH_SHORT
                ).show()
                processDialog.dismiss()

            }
            .addOnProgressListener { taskSnapshot -> //displaying the upload progress
                val progress =
                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                processDialog.setMessage("Uploaded " + progress.toInt() + "%...")
            }
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
                binding.btnImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}