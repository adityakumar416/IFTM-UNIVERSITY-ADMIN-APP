package com.example.iftmadmin.fragment.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iftmadmin.R
import com.example.iftmadmin.databinding.FragmentAddQuizBinding

class AddQuizFragment : Fragment() {
   private lateinit var binding: FragmentAddQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddQuizBinding.inflate(layoutInflater, container, false)

        binding.BasicComputer.setOnClickListener {
            findNavController().navigate(R.id.action_addQuizFragment_to_basicComputerQuizFragment)
        }
        binding.Android.setOnClickListener {
            findNavController().navigate(R.id.action_addQuizFragment_to_androidQuizFragment)
        }

        return binding.root
    }


}