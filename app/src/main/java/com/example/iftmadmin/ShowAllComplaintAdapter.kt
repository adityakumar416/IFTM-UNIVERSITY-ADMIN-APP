package com.example.iftmadmin

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
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

class ShowAllComplaintAdapter(
    private val complaintList: ArrayList<ShowAllComplaintModel>,
    private val context: Context?=null
): RecyclerView.Adapter<ShowAllComplaintAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val courseImage : ImageView = view.findViewById(R.id.course_image)
        val courseName : TextView = view.findViewById(R.id.course_name)
       /* val courseDuration :TextView = view.findViewById(R.id.course_duration)
        val courseFees : TextView = view.findViewById(R.id.course_fees)*/
        val card : MaterialCardView = view.findViewById(R.id.materialCardView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.complaint_view_layout,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val showAllComplaintCurrentUser = complaintList[position]

            holder.courseName.setText("  "+showAllComplaintCurrentUser.complaint)
     /*   holder.courseDuration.setText("  "+courseModel.courseDuration)
        holder.courseFees.setText("  "+courseModel.courseFees)*/

        holder.courseImage.setOnClickListener {
            val action = ViewAllUserComplaintFragmentDirections.actionViewAllUserComplaintFragmentToShowFullImageFragment(showAllComplaintCurrentUser)
            holder.itemView.findNavController().navigate(action)
        }

        Picasso
            .get()
            .load(showAllComplaintCurrentUser.complaintImage)
            .into(holder.courseImage)
/*
            Glide
                .with(Context)
            .load(imageModel.url)
            .into(holder.image)*/

        holder.card.setOnLongClickListener(OnLongClickListener {
            showDialog(showAllComplaintCurrentUser)
            true
        })


    }

    private fun showDialog(showAllComplaintModel: ShowAllComplaintModel) {
        val firebaseStorage = FirebaseStorage.getInstance().getReference("reportComplaint")
        val databaseRef = FirebaseDatabase.getInstance().getReference("reportComplaint")

        if (context != null) {
            MaterialAlertDialogBuilder(context)
                .setTitle("Delete Complaint")
                .setMessage("Do you want to delete this Complaint ?")
                .setNegativeButton("No", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        firebaseStorage.storage.getReferenceFromUrl(showAllComplaintModel.complaintImage!!).delete().addOnSuccessListener(object : OnSuccessListener<Void>{
                            override fun onSuccess(p0: Void?) {
                                Toast.makeText(context, "Complaint deleted", Toast.LENGTH_SHORT).show()

                                databaseRef.child(showAllComplaintModel.complaintId.toString()).removeValue()
                                complaintList.remove(showAllComplaintModel)

                            }
                        })
                            .addOnFailureListener { }
                    }

                }).show()
        }
    }



    override fun getItemCount(): Int {

            return complaintList.size
    }
}



