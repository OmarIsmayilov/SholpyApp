package com.example.sholpyapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.api.ApiUtils
import com.example.sholpyapp.databinding.FragmentFilterBinding
import com.example.sholpyapp.model.AllProductsResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentFilterBinding?=null
    private val binding get() = _binding!!
    private lateinit var  sp : SharedPreferences

    private var category: String ?=null
    private var sort: String = "asc"
    private var limit: Int = 20



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        with(binding){
            rgCategory.setOnCheckedChangeListener { group, i ->
                when(i){
                    R.id.rbAll -> category = null
                    R.id.rbMen-> category = "men's clothing"
                    R.id.rbWomen-> category = "women's clothing"
                    R.id.rbElec-> category = "electronics"
                    R.id.rbJewelery-> category = "jewelery"
                }
            }
            rgSort.setOnCheckedChangeListener { group, i ->
                when(i){
                    R.id.rbAsc-> sort = "asc"
                    R.id.rbDesc-> sort = "desc"
                }
            }
            rgLimit.setOnCheckedChangeListener { group, i ->
                when(i){
                    R.id.rbLimitAll -> limit=20
                    R.id.rbLimit5 -> limit = 5
                    R.id.rbLimit10 -> limit = 10
                }
                if (i==R.id.rbLimitCustom){
                    etLimit.visibility = View.VISIBLE
                    etLimit.requestFocus()
                }else{
                    etLimit.visibility = View.GONE

                }
            }

            btnFilter.setOnClickListener {
                checkData()
            }
        }
    }

    private fun checkData() {
        if (binding.rbLimitCustom.isChecked){
            val customLimit = binding.etLimit.text.toString().trim()
            if (customLimit.isEmpty()){
                Toast.makeText(requireContext(),"Enter limit",Toast.LENGTH_SHORT).show()
            }else{
                limit = customLimit.toInt()
                saveToSp()
            }
        }else{
            saveToSp()
        }

    }

    private fun saveToSp() {
        sp = requireContext().getSharedPreferences("filters", Context.MODE_PRIVATE)
        sp.edit().putString("category",category).apply()
        sp.edit().putInt("limit",limit).apply()
        sp.edit().putString("sort",sort).apply()
        findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToFilterProductFragment())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}