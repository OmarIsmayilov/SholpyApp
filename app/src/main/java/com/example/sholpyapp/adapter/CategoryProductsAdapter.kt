package com.example.sholpyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.ProductItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.ui.fragments.CategoryProductFragmentDirections
import com.example.sholpyapp.ui.fragments.HomeFragmentDirections
import com.squareup.picasso.Picasso

class CategoryProductsAdapter : RecyclerView.Adapter<CategoryProductsAdapter.productCHolder>(){
    private var productsList : ArrayList<AllProductsResponseItem> = arrayListOf()

    inner class productCHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productCHolder {
        val layout = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return productCHolder(layout)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setFilteredList(newProductList: ArrayList<AllProductsResponseItem>){
        this.productsList = newProductList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: productCHolder, position: Int) {
        val product = productsList[position]

        with(holder.binding){
            tvName.text = product.title
            tvPrice.text = "$ ${product.price}"
            Picasso.get().load(product.image).into(shapeableImageView)
            ibAdd.setOnClickListener { Navigation.findNavController(it).navigate(
                CategoryProductFragmentDirections.actionCategoryProductFragmentToDetailFragment(product)) }
        }
    }




    fun updateList(newList : ArrayList<AllProductsResponseItem>){
        productsList.clear()
        productsList.addAll(newList)
        notifyDataSetChanged()
    }


}