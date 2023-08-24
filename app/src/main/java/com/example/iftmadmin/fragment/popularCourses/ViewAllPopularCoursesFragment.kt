package com.example.iftmadmin.fragment.popularCourses

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
import com.example.iftmadmin.fragment.courses.CourseModel
import com.example.iftmadmin.fragment.courses.ShowCoursesAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ViewAllPopularCoursesFragment : Fragment() {
    private lateinit var binding: FragmentViewAllCoursesBinding
    private lateinit var courseList: ArrayList<CourseModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAllCoursesBinding.inflate(layoutInflater, container, false)


        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        courseList = arrayListOf()

        val databaseReference = FirebaseDatabase.getInstance().getReference("popularCourses")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                Log.i(ContentValues.TAG, "User Image $snapshot")
                for (dataSnapshot in snapshot.children) {

                    val courseModel: CourseModel? = dataSnapshot.getValue(CourseModel::class.java)
                    if (courseModel != null) {
                        courseList.add(courseModel)
                    }

                }


                binding.recyclerview.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)

                binding.recyclerview.adapter = ShowCoursesAdapter(courseList,context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }


        })




        return binding.root
    }

}