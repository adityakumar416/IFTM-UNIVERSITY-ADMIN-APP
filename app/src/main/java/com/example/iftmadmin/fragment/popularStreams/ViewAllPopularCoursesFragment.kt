package com.example.iftmadmin.fragment.popularStreams

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
import com.example.iftmadmin.databinding.FragmentViewAllPopularCoursesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ViewAllPopularCoursesFragment : Fragment() {
    private lateinit var binding: FragmentViewAllPopularCoursesBinding
    private lateinit var streamCourseList: ArrayList<ShowAllPopularStreamsCourseModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAllPopularCoursesBinding.inflate(layoutInflater, container, false)


        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        streamCourseList = arrayListOf()

        val databaseReference = FirebaseDatabase.getInstance().getReference("popularStreams")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                streamCourseList.clear()
                Log.i(ContentValues.TAG, "User Image $snapshot")
                for (dataSnapshot in snapshot.children) {

                    val showAllPopularStreamsCourseModel: ShowAllPopularStreamsCourseModel? = dataSnapshot.getValue(ShowAllPopularStreamsCourseModel::class.java)
                    if (showAllPopularStreamsCourseModel != null) {
                        streamCourseList.add(showAllPopularStreamsCourseModel)
                    }

                }


                binding.recyclerview.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)

                binding.recyclerview.adapter = ShowAllPopularCoursesAdapter(streamCourseList,context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }


        })




        return binding.root
    }

}