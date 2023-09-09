package com.example.sholpyapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun observeEvents() {

    }

    override fun onCreateFinish() {
        checkAuth()
    }

    override fun setupListeners() {
        with(binding) {
            btnSignUp.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment2())
            }
            btnSignIn.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }

    private fun checkAuth() {
        val login = sharedPreferences.getBoolean("session",false)
        if(login){
          findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }
    }


}