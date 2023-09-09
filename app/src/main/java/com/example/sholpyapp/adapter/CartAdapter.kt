package com.example.sholpyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.CartItemBinding
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.utils.Extensions.loadUrl
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

class CartAdapter : RecyclerView.Adapter<CartAdapter.cartHolder>() {
    var onClickPlus: (Double) -> Unit = {}
    var onClickMinus: (Double) -> Unit = {}

    inner class cartHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            with(binding) {
                sivProd2.loadUrl(item.image)
                product = item
                executePendingBindings()
                var itemCount = 1
                count = itemCount
                price = item.price

                ibDec.setOnClickListener {
                    if (itemCount > 1) {
                        itemCount -= 1
                        count = itemCount
                        onClickMinus(item.price)
                    }

                }
                ibInc.setOnClickListener {
                    itemCount += 1
                    count = itemCount
                    onClickPlus(item.price)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartHolder {
        val layout = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return cartHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: cartHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }


    val differ = AsyncListDiffer(this, diffUtilCallback)
    object diffUtilCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

    }

}







