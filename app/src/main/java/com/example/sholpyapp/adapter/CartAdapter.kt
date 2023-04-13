package com.example.sholpyapp.adapter

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.CartItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.ui.fragments.CartFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class CartAdapter() : RecyclerView.Adapter<CartAdapter.cartHolder>() {
    private var cartList: ArrayList<AllProductsResponseItem> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid


    inner class cartHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartHolder {
        val layout = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return cartHolder(layout)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    override fun onBindViewHolder(holder: cartHolder, position: Int) {
        val product = cartList[position]

        with(holder.binding) {
            Picasso.get().load(product.image).into(sivProd2)
            tvName2.text = product.title

            db.collection("carts").document(uid.toString()).collection("products")
                .document(product.id.toString()).get().addOnSuccessListener { snapshot ->
                    if (snapshot!=null){
                        var price = snapshot.get("price").toString().toFloat()
                        var quantity = snapshot.get("quantity").toString().toInt()

                        holder.binding.tvQuantity.text = quantity.toString()
                        holder.binding.tvPrice2.text = "$ %.2f".format(price)

                        ibInc.setOnClickListener {
                            price = snapshot.get("price").toString().toFloat()
                            quantity++
                            price = product.price!!.toFloat() *quantity
                            updateQuantityAndPrice(product, quantity, price, holder)
                        }

                        ibDec.setOnClickListener {
                            price = snapshot.get("price").toString().toFloat()

                            if (quantity > 1) {
                                quantity -= 1
                                price = product.price!!.toFloat() * quantity
                                updateQuantityAndPrice(product, quantity, price, holder)
                            }
                        }
                    }

                    }
                }

    }

    private fun updateQuantityAndPrice(product: AllProductsResponseItem, quantity: Int, price: Float, holder: cartHolder) {
        db.collection("carts").document(uid.toString()).collection("products")
            .document(product.id.toString()).update("price",price,"quantity",quantity)
            .addOnSuccessListener {
                holder.binding.tvQuantity.text = quantity.toString()
                holder.binding.tvPrice2.text = "$ %.2f".format(price)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }

    fun setFilteredList(newProductList: ArrayList<AllProductsResponseItem>){
        this.cartList = newProductList
        notifyDataSetChanged()
    }

    fun updateList(newList: ArrayList<AllProductsResponseItem>) {
        cartList.clear()
        cartList.addAll(newList)
        notifyDataSetChanged()
    }


    fun deleteItem(position: Int) {
        db.collection("carts").document(uid.toString()).collection("products")
            .document(cartList[position].id.toString()).delete()
        cartList.removeAt(position)
        notifyDataSetChanged()
    }
}






