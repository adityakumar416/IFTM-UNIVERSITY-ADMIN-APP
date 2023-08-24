package com.example.iftmadmin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iftmadmin.R
import com.example.iftmadmin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.banner.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addBannerFragment)
        }
        binding.viewAllBanner.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllBannerFragment)
        }
        binding.addPopularCourse.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addPopularCourseFragment)
        }
        binding.viewAllPopularCourses.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllPopularCoursesFragment)
        }
        binding.addCourse.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addCoursesFragment)
        }
        binding.viewAllCourses.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllCoursesFragment)
        }
        binding.addQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addQuizFragment)
        }
        binding.viewAllQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllQuizFragment)
        }

        return binding.root
    }

}