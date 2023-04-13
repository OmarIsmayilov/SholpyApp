package com.example.sholpyapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.ProductItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.ui.fragments.HomeFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ProductsAdapter  : RecyclerView.Adapter<ProductsAdapter.productHolder>(){
    private var productsList : ArrayList<AllProductsResponseItem> = arrayListOf()

    inner class productHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productHolder {
        val layout = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return productHolder(layout)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: productHolder, position: Int) {
        val product = productsList[position]

        with(holder.binding){
            tvName.text = product.title
            tvPrice.text = "$ ${product.price}"
            Picasso.get().load(product.image).into(shapeableImageView)
            ibAdd.setOnClickListener { Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)) }
        }
    }




    fun updateList(newList : ArrayList<AllProductsResponseItem>){
        productsList.clear()
        productsList.addAll(newList)
        notifyDataSetChanged()
    }


}