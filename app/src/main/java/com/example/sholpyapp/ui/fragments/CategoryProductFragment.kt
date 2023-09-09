package com.example.sholpyapp.ui.fragments

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sholpyapp.adapter.CategoryProductsAdapter
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentCategoryProductBinding
import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.model.AllProductsResponseItem
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@AndroidEntryPoint
class CategoryProductFragment : BaseFragment<FragmentCategoryProductBinding>(FragmentCategoryProductBinding::inflate) {

    override fun observeEvents() {


    }

    override fun onCreateFinish() {
        //dialog(true)
        getData()
    }

    override fun setupListeners() {
        cAdapter.onClick = {
            findNavController().navigate(CategoryProductFragmentDirections.actionCategoryProductFragmentToDetailFragment2(it.id))
        }
    }

    private val args: CategoryProductFragmentArgs by navArgs()
    //private val utils = ApiUtils.getApi()
    private val cAdapter = CategoryProductsAdapter()
    private lateinit var searchView: SearchView
    private lateinit var productList: ArrayList<AllProductsResponseItem>


    private fun getData() {
        val category = args.category
        productList = arrayListOf()

        searchView = binding.svCategory
        binding.tvCategory.text = category.capitalize(Locale.ROOT)
        binding.rvProductC.adapter = cAdapter
        binding.ibBack.setOnClickListener { findNavController().popBackStack() }
        //setData(category)

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


   /* private fun setData(category: String) {
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
*//*
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

*/

}