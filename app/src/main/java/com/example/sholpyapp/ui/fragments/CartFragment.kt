package com.example.sholpyapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.CartAdapter
import com.example.sholpyapp.adapter.SwipeHelper
import com.example.sholpyapp.databinding.FragmentCartBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.properties.Delegates

class CartFragment : Fragment() {

    private var _binding : FragmentCartBinding?=null
    private val binding get() = _binding!!

    private val cAdapter = CartAdapter()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val cartList = arrayListOf<AllProductsResponseItem>()
    private lateinit var searchView: SearchView
    private lateinit var productList: ArrayList<AllProductsResponseItem>
    private var totalPrice = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCart.adapter = cAdapter
        totalPrice = 0.0
        dialog(true)
        getDataFromFb()

    }


    private fun getDataFromFb() {
        productList = arrayListOf()
        searchView =binding.svCart
        if (auth.currentUser!=null){
            db.collection("carts").document(auth.currentUser!!.uid).collection("products").get().addOnSuccessListener {
                for( data in it){
                    val item = data.toObject(AllProductsResponseItem::class.java)
                    val product = AllProductsResponseItem(
                        item.category,
                        item.description,
                        item.id,
                        item.image,
                        item.price,
                        item.rating,
                        item.title,
                        item.quantity
                    )
                    cartList.add(product)
                    productList.add(product)

                    totalPrice = totalPrice + item.price!!
                    binding.tvTotal.text = "$ %.2f".format(totalPrice)

                }
                cAdapter.updateList(cartList)
                dialog(false)
                binding.ibBack.setOnClickListener { findNavController().popBackStack() }
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

            db.collection("carts").document(auth.currentUser!!.uid).collection("products")
                .addSnapshotListener { value, error ->
                        if(value!=null){
                            for (document in value.documents) {
                                val item = document.toObject(AllProductsResponseItem::class.java)
                                if (item != null) {
                                    if (value.size() == 0){
                                        binding.tvTotal.text =  "$ 0.0"
                                    }else{
                                        if (item.price != null) {
                                            totalPrice += item.price!!
                                        }
                                    }

                                }
                            }
                            binding.tvTotal.text = "$ ${totalPrice.toInt()}"
                            totalPrice = 0.0
                        }

                    }


        }




        val swipeHelper = object  : SwipeHelper(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.LEFT -> {
                        cAdapter.deleteItem(viewHolder.adapterPosition)
                         
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeHelper)
        touchHelper.attachToRecyclerView(binding.rvCart)

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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun dialog(bool:Boolean){
        val pb = binding.progressBar3
        if(bool){
            pb.visibility = View.VISIBLE
        }else{
            pb.visibility = View.INVISIBLE
        }
    }


}