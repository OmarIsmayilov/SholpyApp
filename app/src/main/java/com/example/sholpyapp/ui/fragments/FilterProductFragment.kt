package com.example.sholpyapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.adapter.CategoryProductsAdapter
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentFilterProductBinding
import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.model.AllProductsResponseItem
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class FilterProductFragment : BaseFragment<FragmentFilterProductBinding>(FragmentFilterProductBinding::inflate) {


    private val cAdapter = CategoryProductsAdapter()
    private lateinit var sp : SharedPreferences
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var productList : ArrayList<AllProductsResponseItem>
    override fun observeEvents() {
    }

    override fun onCreateFinish() {
       // dialog(true)
        searchView = binding.svFilteredProduct
        productList = arrayListOf()
        getAllProducts()
    }

    override fun setupListeners() {
        cAdapter.onClick={
            findNavController().navigate(FilterProductFragmentDirections.actionFilterProductFragmentToDetailFragment(it.id))
        }
    }



    private fun getAllProducts() {
        sp = requireContext().getSharedPreferences("filters",Context.MODE_PRIVATE)
        getFilterOptions()
        binding.btnFilter.setOnClickListener { findNavController().navigate(FilterProductFragmentDirections.actionFilterProductFragmentToFilterFragment()) }
        binding.rvFilteredProducts.adapter = cAdapter
        binding.ibBack.setOnClickListener { findNavController().popBackStack() }

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
        if(query != null){
            val filteredList = kotlin.collections.ArrayList<AllProductsResponseItem>()
            for(i in productList){
                if (i.title!!.toLowerCase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()){
                cAdapter.setFilteredList(filteredList)
            }else{
                Toast.makeText(requireContext(),"No found",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getFilterOptions() {
      /*  val category = sp.getString("category",null)
        val limit = sp.getInt("limit",20)
        val sort = sp.getString("sort","asc")
        if(category!=null){
            filterProduct(category,limit,sort)
        }else{
            filterAllProduct(limit,sort)
        }*/
    }

  /*  private fun filterAllProduct(limit: Int, sort: String?) {
        utils.getAllProducts(limit,sort!!).enqueue(object : Callback<AllProductsResponse>{
            override fun onResponse(
                call: Call<AllProductsResponse>,
                response: Response<AllProductsResponse>
            ) {
                if(response.isSuccessful){
                    response.body().let {
                        cAdapter.updateList(it!!)
                        productList = it
                        dialog(false)

                        Log.e("neticesortall", "onResponse: $limit  $sort", )
                    }
                }
            }

            override fun onFailure(call: Call<AllProductsResponse>, t: Throwable) {
                dialog(false)
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })
    }*/

  /*  private fun filterProduct(category : String,limit:Int,sort : String?) {
        utils.getFilteredProducts(category, limit, sort!!).enqueue(object : Callback<AllProductsResponse>{
            override fun onResponse(
                call: Call<AllProductsResponse>,
                response: Response<AllProductsResponse>
            ) {
                if (response.isSuccessful){
                    dialog(false)

                    Log.e("neticesortall", "onResponse: $limit  $sort $category", )
                    response.body().let{ cAdapter.updateList(it!!) }
                }
            }

            override fun onFailure(call: Call<AllProductsResponse>, t: Throwable) {
                dialog(false)

                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })*/

    }

    /*private fun clearSp(){
        sp.edit()
            .remove("category")
            .remove("sort")
            .remove("limit")
            .apply()
        dialog(false)
*/


    /*private fun dialog(bool:Boolean){
        val pb = binding.progressBar6
        if(bool){
            pb.visibility = View.VISIBLE
        }else{
            pb.visibility = View.INVISIBLE
        }
    }*/

