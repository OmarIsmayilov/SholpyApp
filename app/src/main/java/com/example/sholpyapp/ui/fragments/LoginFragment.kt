package com.example.sholpyapp.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvSignUp.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment2()) }
            btnSignIn.setOnClickListener { validateData() }
            ibBack.setOnClickListener { findNavController().popBackStack() }
        }

    }

    private fun validateData() {
        with(binding) {
            val email = etLoginEmail.text.toString().trim()
            val password = etLPass.text.toString().trim()


            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                snackbar("Enter valid email")
                etLoginEmail.requestFocus()
            } else if (password.isEmpty()) {
                etLPass.requestFocus()
                snackbar("Enter password")
            } else {
                signIn(email, password)
                dialog(true)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                saveToSp(auth.currentUser?.uid)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                dialog(false)
            }
            .addOnFailureListener {
                it.localizedMessage?.let { it1 -> toast(it1) }
                dialog(false)
            }
    }

    private fun saveToSp(uid: String?) {
        val sp = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        sp.edit().putString("uid",uid).apply()
    }

    private fun snackbar(message: String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.BLUE)
        snackbar.setTextColor(Color.WHITE)
        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackbar.show()

    }
    private fun toast(message: String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
    private fun dialog(bool:Boolean){
        val pb = binding.progressBar
        if(bool){
            pb.visibility = View.VISIBLE
        }else{
            pb.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}