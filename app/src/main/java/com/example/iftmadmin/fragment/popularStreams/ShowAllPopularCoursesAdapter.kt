package com.example.iftmadmin.fragment.popularStreams

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.iftmadmin.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlin.Int
import kotlin.toString

class ShowAllPopularCoursesAdapter(
    private val coursesStreamList: ArrayList<ShowAllPopularStreamsCourseModel>,
    private val context: Context?=null
): RecyclerView.Adapter<ShowAllPopularCoursesAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val courseImage : ImageView = view.findViewById(R.id.course_image)
        val courseName : TextView = view.findViewById(R.id.course_name)
       /* val courseDuration :TextView = view.findViewById(R.id.course_duration)
        val courseFees : TextView = view.findViewById(R.id.course_fees)*/
        val card : MaterialCardView = view.findViewById(R.id.materialCardView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_view_layout,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val popularStreamsCourseModel: ShowAllPopularStreamsCourseModel = coursesStreamList[position]

            holder.courseName.setText("  "+popularStreamsCourseModel.courseName)
     /*   holder.courseDuration.setText("  "+courseModel.courseDuration)
        holder.courseFees.setText("  "+courseModel.courseFees)*/



        Picasso
            .get()
            .load(popularStreamsCourseModel.courseImage)
            .into(holder.courseImage)
/*
            Glide
                .with(Context)
            .load(imageModel.url)
            .into(holder.image)*/

        holder.card.setOnLongClickListener(OnLongClickListener {
            showDialog(popularStreamsCourseModel)
            true
        })


    }

    private fun showDialog(popularStreamsCourseModel: ShowAllPopularStreamsCourseModel) {
        val firebaseStorage = FirebaseStorage.getInstance().getReference("popularStreams")
        val databaseRef = FirebaseDatabase.getInstance().getReference("popularStreams")

        if (context != null) {
            MaterialAlertDialogBuilder(context)
                .setTitle("Delete Course")
                .setMessage("Do you want to delete this Course ?")
                .setNegativeButton("No", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        firebaseStorage.storage.getReferenceFromUrl(popularStreamsCourseModel.courseImage!!).delete().addOnSuccessListener(object : OnSuccessListener<Void>{
                            override fun onSuccess(p0: Void?) {
                                Toast.makeText(context, "Course deleted", Toast.LENGTH_SHORT).show()

                                databaseRef.child(popularStreamsCourseModel.courseId.toString()).removeValue()
                                coursesStreamList.remove(popularStreamsCourseModel)

                            }
                        })
                            .addOnFailureListener { }
                    }

                }).show()
        }
    }



    override fun getItemCount(): Int {

            return coursesStreamList.size
    }
}



