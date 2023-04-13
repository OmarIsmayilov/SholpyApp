package com.example.sholpyapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnSignUp.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment2())
            }
            btnSignIn.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}