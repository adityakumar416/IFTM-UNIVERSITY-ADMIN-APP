package com.example.iftmadmin.fragment.quiz.basicComputerQuiz

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.iftmadmin.databinding.FragmentBasicComputerQuizBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BasicComputerQuizFragment : Fragment() {
   private lateinit var binding: FragmentBasicComputerQuizBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicComputerQuizBinding.inflate(layoutInflater, container, false)

        binding.uploadQuestionBtn.setOnClickListener {

            val question =binding.question.text.toString()
            val optionA =binding.optionA.text.toString()
            val optionB =binding.optionB.text.toString()
            val optionC =binding.optionC.text.toString()
            val optionD =binding.optionD.text.toString()
            val answer =binding.questionAnswer.text.toString()

            if(binding.question.text!!.isEmpty()){
                binding.question.requestFocus()
                Toast.makeText(context,"Question is Mandatory", Toast.LENGTH_SHORT).show()
            }
            else if(binding.optionA.text!!.isEmpty()){
                binding.optionA.requestFocus()
                Toast.makeText(context,"Option A is Mandatory", Toast.LENGTH_SHORT).show()
            }
            else if(binding.optionB.text!!.isEmpty()){
                binding.optionB.requestFocus()
                Toast.makeText(context,"Option B is Mandatory", Toast.LENGTH_SHORT).show()
            }
            else if(binding.optionC.text!!.isEmpty()){
                binding.optionC.requestFocus()
                Toast.makeText(context,"Option C is Mandatory", Toast.LENGTH_SHORT).show()
            }
            else if(binding.optionD.text!!.isEmpty()){
                binding.optionD.requestFocus()
                Toast.makeText(context,"Option D is Mandatory", Toast.LENGTH_SHORT).show()
            }
            else if(binding.questionAnswer.text!!.isEmpty()){
                binding.questionAnswer.requestFocus()
                Toast.makeText(context,"Answer is Mandatory", Toast.LENGTH_SHORT).show()
            }
            else{

                addDataToFirebase(question, optionA, optionB,optionC,optionD,answer)
            }
        }


        return binding.root
    }

    private fun addDataToFirebase(question: String, optionA: String, optionB: String, optionC: String, optionD: String, answer: String) {



        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("basicComputerQuiz")

            firebaseDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    Log.i(ContentValues.TAG, "onSuccess Main: $snapshot")

                    Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show()

                    val basicComputerQuizModel = BasicComputerQuizModel(
                        question,
                        optionA,
                        optionB,
                        optionC,
                        optionD,
                        answer
                    )
                    val uploadId = basicComputerQuizModel.question
                    firebaseDatabase.child(uploadId.toString()).setValue(basicComputerQuizModel)


                    binding.question.text = null
                    binding.optionA.text= null
                    binding.optionB.text= null
                    binding.optionC.text= null
                    binding.optionD.text= null
                    binding.questionAnswer.text= null
                }


                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        context,
                        "Failed to Upload Image",
                        Toast.LENGTH_SHORT
                    ).show()                }

            })




    }

}