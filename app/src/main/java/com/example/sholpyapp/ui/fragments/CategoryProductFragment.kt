package com.example.sholpyapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.CategoryProductsAdapter
import com.example.sholpyapp.adapter.ProductsAdapter
import com.example.sholpyapp.api.ApiUtils
import com.example.sholpyapp.databinding.FragmentCategoryProductBinding
import com.example.sholpyapp.databinding.FragmentDetailBinding
import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.model.AllProductsResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CategoryProductFragment : Fragment() {
    private var _binding: FragmentCategoryProductBinding? = null
    private val binding get() = _binding!!
    private val args: CategoryProductFragmentArgs by navArgs()
    private val utils = ApiUtils.getApi()
    private val cAdapter = CategoryProductsAdapter()
    private lateinit var searchView: SearchView
    private lateinit var productList: ArrayList<AllProductsResponseItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoryProductBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog(true)
        getData()

    }

    private fun getData() {
        val category = args.category
        productList = arrayListOf()

        searchView = binding.svCategory
        binding.tvCategory.text = category.capitalize(Locale.ROOT)
        binding.rvProductC.adapter = cAdapter
        binding.ibBack.setOnClickListener { findNavController().popBackStack() }
        setData(category)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSearchList(newText)
                return true
            }

        })
    }


    private fun filterSearchList(query: String?) {
        if (query != null) {
            val filteredList = kotlin.collections.ArrayList<AllProductsResponseItem>()
            for (i in productList) {
                if (i.title!!.toLowerCase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                cAdapter.setFilteredList(filteredList)
            } else {
                Toast.makeText(requireContext(), "No found", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun setData(category: String) {
        utils.getProductbyCategory(category).enqueue(object : Callback<AllProductsResponse> {
            override fun onResponse(
                call: Call<AllProductsResponse>,
                response: Response<AllProductsResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        cAdapter.updateList(it!!)
                        productList = it
                        dialog(false)
                    }
                }
            }

            override fun onFailure(call: Call<AllProductsResponse>, t: Throwable) {
                dialog(false)
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun dialog(bool: Boolean) {
        val pb = binding.progressBar5
        if (bool) {
            pb.visibility = View.VISIBLE
        } else {
            pb.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}