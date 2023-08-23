package com.example.iftmadmin

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ShowImageAdapter(
    private val imageList: ArrayList<ImageModel>,
    private val context: Context? = null
): RecyclerView.Adapter<ShowImageAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val image :ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.banner_list_layout,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val imageModel:ImageModel = imageList[position]




        Picasso
            .get()
            .load(imageModel.url)
            .into(holder.image)
/*
            Glide
                .with(Context)
            .load(imageModel.url)
            .into(holder.image)*/

        holder.itemView.setOnLongClickListener(OnLongClickListener {
            showDialog(imageModel)
            true
        })


    }

    private fun showDialog(imageModel: ImageModel) {
        val firebaseStorage = FirebaseStorage.getInstance().getReference("banners")
        val databaseRef = FirebaseDatabase.getInstance().getReference("banners")

        if (context != null) {
            MaterialAlertDialogBuilder(context)
                .setTitle("Delete Banner")
                .setMessage("Do you want to delete this banner ?")
                .setNegativeButton("No", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        firebaseStorage.storage.getReferenceFromUrl(imageModel.url!!).delete().addOnSuccessListener(object : OnSuccessListener<Void>{
                            override fun onSuccess(p0: Void?) {
                                Toast.makeText(context, "Banner deleted", Toast.LENGTH_SHORT).show()

                                databaseRef.child(imageModel.imageId.toString()).removeValue()
                                imageList.remove(imageModel)

                            }
                        })
                            .addOnFailureListener(object :OnFailureListener{
                                override fun onFailure(p0: Exception) {

                                }

                            })
                    }

                }).show()
        }
    }



    override fun getItemCount(): Int {

            return imageList.size
    }
}



