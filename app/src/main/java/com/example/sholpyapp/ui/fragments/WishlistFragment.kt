package com.example.sholpyapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.CartAdapter
import com.example.sholpyapp.adapter.SwipeHelper
import com.example.sholpyapp.adapter.WishlistAdapter
import com.example.sholpyapp.databinding.FragmentLoginBinding
import com.example.sholpyapp.databinding.FragmentWishlistBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.google.firebase.firestore.FirebaseFirestore


class WishlistFragment : Fragment() {

    private var _binding : FragmentWishlistBinding?=null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val wishList = arrayListOf<AllProductsResponseItem>()
    private lateinit var wAdapter : WishlistAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClearAll.setOnClickListener {
            dialog(true)
            clearAllData() }
        wAdapter = WishlistAdapter(requireContext())
        binding.rvWish.adapter = wAdapter
        dialog(true)
        getDataFromFb()

    }

    private fun clearAllData() {
        db.collection("wishlist").get().addOnSuccessListener {docs->
            for (doc in docs){
            doc.reference.delete()
            }
            wishList.clear()
            getDataFromFb()
        }

    }

    private fun getDataFromFb() {
        db.collection("wishlist").get().addOnSuccessListener {docs->
            for (doc in docs){
                val product = doc.toObject(AllProductsResponseItem::class.java)
                val item = AllProductsResponseItem(
                    product.category,
                    product.description,
                    product.id,
                    product.image,
                    product.price,
                    product.rating,
                    product.title,
                    product.quantity,
                )
                wishList.add(item)
            }
            wAdapter.updateList(wishList)
            dialog(false)



            val swipeHelper = object  : SwipeHelper(){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            wAdapter.deleteItem(viewHolder.adapterPosition)

                        }
                    }
                }
            }
            val touchHelper = ItemTouchHelper(swipeHelper)
            touchHelper.attachToRecyclerView(binding.rvWish)

        }
    }

    private fun dialog(bool:Boolean){
        val pb = binding.progressBar4
        if(bool){
            pb.visibility = View.VISIBLE
        }else{
            pb.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}