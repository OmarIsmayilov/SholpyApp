package com.example.sholpyapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.databinding.FragmentProfileBinding
import com.example.sholpyapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding?=null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var sp : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        binding.btnAccount.setOnClickListener { findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAccountFragment()) }
        binding.btnEdit.setOnClickListener { findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAccountFragment()) }
        setName()
        setTheme()
    }

    private fun setName() {
        val uid = auth.currentUser?.uid.toString()
        db.collection("users").document(uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            Picasso.get().load(user?.photoUrl).into(binding.shapeableImageView)
        }
        auth.currentUser?.let {
            db.collection("users").document(it.uid).get().addOnSuccessListener {
                val user = it.data
                if (user != null) {
                    val name = user.get("name")
                    binding.tvUserName.text = name.toString()
                }
            }

        }
        binding.ibBack.setOnClickListener { findNavController().popBackStack() }


    }

    private fun signOut() {
        auth.signOut()
        sp.edit().putString("uid",null).apply()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSplashFragment())
    }

    private fun setTheme() {
        with(binding){
        tvLogout.setOnClickListener {
            signOut()
        }
        switchTheme.setOnCheckedChangeListener { button, b -> if(b){ setDarkMode() }else{ setLightMode() } }
    }
        val theme = sp.getBoolean("theme",false)
        if(theme){ setDarkMode() }else{ setLightMode()}
    }

    fun setLightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sp.edit().putBoolean("theme",false).apply()
        binding.switchTheme.isChecked =false
        binding.switchTheme.setText("Light")
    }

    fun setDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        sp.edit().putBoolean("theme",true).apply()
        binding.switchTheme.isChecked =true
        binding.switchTheme.setText("Dark")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}