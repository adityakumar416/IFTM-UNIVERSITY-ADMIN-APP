package com.example.iftmadmin.fragment.quiz.basicComputerQuiz

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
import com.example.iftmadmin.databinding.FragmentBasicComputerViewAllQuizBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BasicComputerViewAllQuizFragment : Fragment() {
    private lateinit var binding: FragmentBasicComputerViewAllQuizBinding
    private lateinit var basicComputerQuestionList: ArrayList<BasicComputerQuizModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicComputerViewAllQuizBinding.inflate(layoutInflater, container, false)

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        basicComputerQuestionList = arrayListOf()

        val databaseReference = FirebaseDatabase.getInstance().getReference("basicComputerQuiz")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                basicComputerQuestionList.clear()
                Log.i(ContentValues.TAG, "User Image $snapshot")
                for (dataSnapshot in snapshot.children) {

                    val basicComputerQuizModel: BasicComputerQuizModel? = dataSnapshot.getValue(
                        BasicComputerQuizModel::class.java)
                    if (basicComputerQuizModel != null) {
                        basicComputerQuestionList.add(basicComputerQuizModel)
                    }

                }


                binding.recyclerview.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)

                binding.recyclerview.adapter = ShowBasicComputerQuizAdapter(basicComputerQuestionList,context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }


        })



        return binding.root
    }


}