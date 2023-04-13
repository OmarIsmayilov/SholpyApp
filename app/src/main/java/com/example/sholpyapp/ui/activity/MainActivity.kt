package com.example.sholpyapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.CartAdapter
import com.example.sholpyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomMenu,navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.splashFragment,R.id.loginFragment,R.id.registerFragment2 -> binding.bottomMenu.visibility = View.GONE
                else -> binding.bottomMenu.visibility = View.VISIBLE
            }
        }

    }
}