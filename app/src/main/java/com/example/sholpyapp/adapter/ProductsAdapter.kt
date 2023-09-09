package com.example.sholpyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.ProductItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.utils.Extensions.loadUrl

class ProductsAdapter  : RecyclerView.Adapter<ProductsAdapter.productHolder>(){
    var onClick : (Int) -> Unit = {}
    inner class productHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productHolder {
        val layout = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return productHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: productHolder, position: Int) {
        val product = differ.currentList[position]

        with(holder.binding){
            tvName.text = product.title
            tvPrice.text = "$ ${product.price}"
            shapeableImageView.loadUrl(product.image)
            ibAdd.setOnClickListener {
                onClick(product.id)
            }
        }
    }

     val differ = AsyncListDiffer(this,difUtilCallBack)
    object difUtilCallBack : DiffUtil.ItemCallback<AllProductsResponseItem>(){
        override fun areItemsTheSame(
            oldItem: AllProductsResponseItem,
            newItem: AllProductsResponseItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AllProductsResponseItem,
            newItem: AllProductsResponseItem,
        ): Boolean {
            return oldItem == newItem
        }

    }




}