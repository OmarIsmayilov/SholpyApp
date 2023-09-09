package com.example.sholpyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.ProductItemBinding
import com.example.sholpyapp.databinding.RatedProductItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.ui.fragments.HomeFragmentDirections
import com.example.sholpyapp.utils.Extensions.loadUrl

class RatedProductAdapter : RecyclerView.Adapter<RatedProductAdapter.ratedProductHolder>(){
    private var productsList : ArrayList<AllProductsResponseItem> = arrayListOf()
    var onClick : (Int) -> Unit = {}
    inner class ratedProductHolder(val binding: RatedProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ratedProductHolder {
        val layout = RatedProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ratedProductHolder(layout)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ratedProductHolder, position: Int) {
        val item = productsList[position]
        with(holder.binding){
            sivProd.loadUrl(item.image)
            product = item
            btnBuyNow.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    fun updateList(newList : ArrayList<AllProductsResponseItem>){
        productsList.clear()
        productsList.addAll(newList)
        notifyDataSetChanged()
    }


}