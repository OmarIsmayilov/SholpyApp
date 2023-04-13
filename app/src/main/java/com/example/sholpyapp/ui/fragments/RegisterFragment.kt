package com.example.sholpyapp.ui.fragments


import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.databinding.FragmentRegisterBinding
import com.example.sholpyapp.model.User
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var uid: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvSignIn.setOnClickListener { findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment()) }
            btnSignUp.setOnClickListener {
                validateData()
            }
            ibBack.setOnClickListener { findNavController().popBackStack() }
            googleLogin.setOnClickListener {  }
        }


    }


    private fun validateData() {
        with(binding) {
            name = etName.text.toString().trim()
            email = etRegEmail.text.toString().trim()
            password = etRegPass.text.toString().trim()

            if (name.isEmpty()) {
                etName.requestFocus()
                snackbar("Enter name")
            } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                snackbar("Enter valid email")
                etRegEmail.requestFocus()
            } else if (password.isEmpty()) {
                etRegPass.requestFocus()
                snackbar("Enter password")
            } else {
                dialog(true)
                signUp(email, password)
            }
        }


    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                saveToDb()
            }
            .addOnFailureListener {
                 it.localizedMessage?.let { it1 -> toast(it1) }
            }
    }

    private fun saveToDb() {
        uid = auth.currentUser!!.uid
        val timestamp = System.currentTimeMillis().toString()
        val user = User(name, email, uid, timestamp,null)
        db.collection("users").document(uid).set(user)
            .addOnSuccessListener {
                dialog(false)
                toast(" Create account succesfully")
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment())
            }
            .addOnFailureListener {
                dialog(false)
                it.localizedMessage?.let { it1 -> toast(it1) } }
    }


    private fun snackbar(message: String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.BLUE)
        snackbar.setTextColor(Color.WHITE)
        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackbar.show()

    }
    private fun toast(message: String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
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