package com.example.sholpyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.WishlistItemBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class WishlistAdapter(private val context: Context) : RecyclerView.Adapter<WishlistAdapter.WishlistHolder>() {

    private val wishList = arrayListOf<AllProductsResponseItem>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance().currentUser

    inner class WishlistHolder(val binding : WishlistItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistHolder {
        val layout = WishlistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WishlistHolder(layout)
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    override fun onBindViewHolder(holder: WishlistHolder, position: Int) {
        val item = wishList[position]
        with(holder.binding){
            tvName4.text = item.title
            tvPrice4.text = item.price.toString()
            Picasso.get().load(item.image).into(sivProd4)
            btnAdd.setOnClickListener {addToCart(item) }
        }
    }

    private fun addToCart(item: AllProductsResponseItem) {
        if (auth!=null){
            FirebaseAuth.getInstance().currentUser!!.let {
                db.collection("carts").document(it.uid)
                    .collection("products").document(item.id.toString()).set(item)
                    .addOnSuccessListener {
                        Toast.makeText(context,"Added to cart", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener {
                        Toast.makeText(context,it.localizedMessage, Toast.LENGTH_SHORT).show() }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList : ArrayList<AllProductsResponseItem>){
        wishList.clear()
        wishList.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteItem(position : Int){
        val item  =  wishList[position]
        db.collection("wishlist").document(item.id.toString()).delete()
        wishList.removeAt(position)
        notifyDataSetChanged()
    }
}
