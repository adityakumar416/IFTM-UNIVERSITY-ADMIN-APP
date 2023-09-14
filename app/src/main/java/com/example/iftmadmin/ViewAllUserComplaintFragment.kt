package com.example.iftmadmin

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
import com.example.iftmadmin.databinding.FragmentViewAllCoursesBinding
import com.example.iftmadmin.fragment.popularStreams.ShowAllPopularCoursesAdapter
import com.example.iftmadmin.fragment.popularStreams.ShowAllPopularStreamsCourseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ViewAllUserComplaintFragment : Fragment() {
    private lateinit var binding: FragmentViewAllCoursesBinding
    private lateinit var complaintList: ArrayList<ShowAllComplaintModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAllCoursesBinding.inflate(layoutInflater, container, false)


        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        complaintList = arrayListOf()

        val databaseReference = FirebaseDatabase.getInstance().getReference("reportComplaint")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                complaintList.clear()
                Log.i(ContentValues.TAG, "User Image $snapshot")
                for (dataSnapshot in snapshot.children) {

                    val showAllComplaintModel:ShowAllComplaintModel? = dataSnapshot.getValue(ShowAllComplaintModel::class.java)
                    if (showAllComplaintModel != null) {
                        complaintList.add(showAllComplaintModel)
                    }

                }


                binding.recyclerview.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)

                binding.recyclerview.adapter = ShowAllComplaintAdapter(complaintList,context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }


        })




        return binding.root
    }

}