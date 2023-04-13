package com.example.sholpyapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.ProductsAdapter
import com.example.sholpyapp.adapter.RatedProductAdapter
import com.example.sholpyapp.api.ApiUtils
import com.example.sholpyapp.databinding.FragmentHomeBinding
import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sp: SharedPreferences
    private val utils = ApiUtils.getApi()
    private val pAdapter = ProductsAdapter()
    private val rAdapter = RatedProductAdapter()
    private var category: String = ""
    private lateinit var pb: ProgressBar
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        checkAuth()

    }

    private fun toCategoryFragment(category: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryProductFragment(
                category
            )
        )
    }

    private fun setTheme() {

        with(binding) {
            tvShow.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFilterProductFragment()) }
            ivMen.setOnClickListener {
                category = "men's clothing"
                toCategoryFragment(category)
            }
            ivWomen.setOnClickListener {
                category = "women's clothing"
                toCategoryFragment(category)
            }
            ivElectronics.setOnClickListener {
                category = "electronics"
                toCategoryFragment(category)
            }
            ivJewelery.setOnClickListener {
                category = "jewelery"
                toCategoryFragment(category)
            }
        }
        val theme = sp.getBoolean("theme", false)
        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding.ivProfil.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment()) }

    }

    private fun checkAuth() {
        val uid = sp.getString("uid", null)
        val fUid = FirebaseAuth.getInstance().currentUser
        if (uid != null && fUid!=null ) {
            setTheme()
            binding.rvProducts.adapter = pAdapter
            binding.rvRatedProducts.adapter = rAdapter
            getData()
        } else {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSplashFragment())
        }
    }

    private fun getData() {
        pb = binding.progressBar2
        dialog(true)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid!=null){
            db.collection("users").document(uid.toString()).get().addOnSuccessListener {
                val user = it.toObject(User::class.java)
                Picasso.get().load(user?.photoUrl).into(binding.ivProfil)
            }.addOnFailureListener {
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }


        utils.getAllProducts(20, "asc").enqueue(object : Callback<AllProductsResponse> {
            override fun onResponse(
                call: Call<AllProductsResponse>,
                response: Response<AllProductsResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        pAdapter.updateList(it!!)
                    }
                }
            }

            override fun onFailure(call: Call<AllProductsResponse>, t: Throwable) {

                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        utils.getRatedProducts(5).enqueue(object : Callback<AllProductsResponse> {
            override fun onResponse(
                call: Call<AllProductsResponse>,
                response: Response<AllProductsResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        dialog(false)
                        rAdapter.updateList(it!!)

                    }
                }
            }

            override fun onFailure(call: Call<AllProductsResponse>, t: Throwable) {

                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun dialog(bool: Boolean) {

        if (bool) {
            pb.visibility = View.VISIBLE
        } else {
            pb.visibility = View.INVISIBLE
        }
    }



}