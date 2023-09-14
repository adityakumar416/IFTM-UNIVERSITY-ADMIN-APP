package com.example.iftmadmin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.iftmadmin.R
import com.example.iftmadmin.databinding.FragmentShowFullImageBinding
import com.squareup.picasso.Picasso

class ShowFullImageFragment : Fragment() {
 private lateinit var binding : FragmentShowFullImageBinding
    private val args by navArgs<ShowFullImageFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowFullImageBinding.inflate(layoutInflater, container, false)

        val complaintImage = args.currentUser.complaintImage

        Picasso
            .get()
            .load(complaintImage)
            .into(binding.imageView)

        return binding.root
    }
}