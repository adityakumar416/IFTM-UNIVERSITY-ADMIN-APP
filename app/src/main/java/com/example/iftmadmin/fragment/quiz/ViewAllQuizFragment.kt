package com.example.iftmadmin.fragment.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iftmadmin.fragment.quiz.basicComputerQuiz.BasicComputerQuizModel
import com.example.iftmadmin.R
import com.example.iftmadmin.databinding.FragmentViewAllQuizBinding


class ViewAllQuizFragment : Fragment() {
   private lateinit var binding: FragmentViewAllQuizBinding
    private lateinit var basicComputerQuestionList: ArrayList<BasicComputerQuizModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAllQuizBinding.inflate(layoutInflater, container, false)

        binding.BasicComputer.setOnClickListener {
            findNavController().navigate(R.id.action_viewAllQuizFragment_to_basicComputerViewAllQuizFragment)

        }
        binding.Android.setOnClickListener {
            findNavController().navigate(R.id.action_viewAllQuizFragment_to_viewAllAndroidQuizFragment)

        }
        return binding.root
    }


}