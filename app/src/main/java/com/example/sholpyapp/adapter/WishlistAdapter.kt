package com.example.sholpyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.WishlistItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.utils.Extensions.loadUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistHolder>() {

    inner class WishlistHolder(val binding: WishlistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistHolder {
        val layout = WishlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: WishlistHolder, position: Int) {
        val item = differ.currentList[position]
        with(holder.binding) {
            tvName4.text = item.title
            tvPrice4.text = item.price.toString()
            sivProd4.loadUrl(item.image)
        }
    }

    val differ = AsyncListDiffer(this,diffUtilCallback)
    object diffUtilCallback : DiffUtil.ItemCallback<WishlistItem>() {
        override fun areItemsTheSame(oldItem: WishlistItem, newItem: WishlistItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WishlistItem, newItem: WishlistItem): Boolean {
            return oldItem == newItem
        }

    }

}
