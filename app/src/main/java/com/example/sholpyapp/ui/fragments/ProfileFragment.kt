package com.example.sholpyapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentProfileBinding
import com.example.sholpyapp.model.User
import com.example.sholpyapp.viewmodel.LoginViewModel
import com.example.sholpyapp.utils.Extensions.loadUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var sp : SharedPreferences
    private val viewModel : LoginViewModel by viewModels()
    override fun observeEvents() {

    }

    override fun onCreateFinish() {
        setName()
        setTheme()
    }

    override fun setupListeners() {
        binding.btnAccount.setOnClickListener { findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAccountFragment()) }
        binding.btnEdit.setOnClickListener { findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAccountFragment()) }

    }

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    private fun setName() {
        val uid = auth.currentUser?.uid.toString()
        db.collection("users").document(uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            binding.shapeableImageView.loadUrl(user?.photoUrl)
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
        deleteSession()
        viewModel.signOutUser()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSplashFragment())
    }

    private fun deleteSession() {
        sp.edit().putBoolean("session",false).apply()
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



}