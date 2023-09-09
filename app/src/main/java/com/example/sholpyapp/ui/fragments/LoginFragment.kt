package com.example.sholpyapp.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentLoginBinding
import com.example.sholpyapp.viewmodel.LoginViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel: LoginViewModel by viewModels()
    override fun observeEvents() {
        with(binding){
            with(viewModel){

                loading.observe(viewLifecycleOwner){
                    progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }

                resultAuth.observe(viewLifecycleOwner) {
                    it?.let {
                        FancyToast.makeText(
                            requireContext(),
                            it.message,
                            FancyToast.LENGTH_SHORT,
                            if (it.success) FancyToast.SUCCESS else FancyToast.ERROR,
                            false
                        ).show()
                        if (it.success) {
                            setSession()
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }
                    }

                }
            }
        }
    }

    override fun onCreateFinish() {

    }

    override fun setupListeners() {
        with(binding) {
            tvSignUp.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment2()) }
            btnSignIn.setOnClickListener { login() }
            ibBack.setOnClickListener { findNavController().popBackStack() }
        }
    }


    private fun validateData(email: String?, password: String?): Boolean {
        return when {
            email.isNullOrEmpty() -> false
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            password.isNullOrEmpty() -> false
            else -> true
        }
    }


    private fun login() {
        with(binding) {
            val email = etLoginEmail.text.toString().trim()
            val password = etLPass.text.toString().trim()

            if(validateData(email, password)){
                viewModel.signIn(email, password)
            }else{
                FancyToast.makeText(
                    requireContext(),
                   "Fields cannot be empty",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    false
                ).show()
            }
        }

    }


    private fun setSession() {
        sharedPreferences.edit().putBoolean("session", true).apply()
    }


}