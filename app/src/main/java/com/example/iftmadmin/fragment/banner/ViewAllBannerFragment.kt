package com.example.iftmadmin.fragment.banner

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iftmadmin.databinding.FragmentViewAllBannerBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewAllBannerFragment : Fragment() {
  private lateinit var binding: FragmentViewAllBannerBinding
    private lateinit var imageList: ArrayList<BannerModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAllBannerBinding.inflate(layoutInflater, container, false)


        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        imageList = arrayListOf()

        val databaseReference = FirebaseDatabase.getInstance().getReference("banners")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imageList.clear()
                Log.i(ContentValues.TAG, "User Image $snapshot")
                for (dataSnapshot in snapshot.children) {

                    val image: BannerModel? = dataSnapshot.getValue(BannerModel::class.java)
                    if (image != null) {
                        imageList.add(image)
                    }

                }


                binding.recyclerview.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)

                binding.recyclerview.adapter = ShowBannerAdapter(imageList, context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }


        })


        return binding.root
    }

}