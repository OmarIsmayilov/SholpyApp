package com.example.sholpyapp.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.databinding.ImageItemBinding
import com.example.sholpyapp.ui.fragments.ImagesFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ImageAdapter() : RecyclerView.Adapter<ImageAdapter.imageHolder>()  {
    private val imageList = arrayListOf<Uri>()
    private val db = FirebaseFirestore.getInstance()
    inner class imageHolder(val binding : ImageItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageHolder {
        val ly = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return imageHolder(ly)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: imageHolder, position: Int) {
        val image = imageList[position]
        Picasso.get().load(image.toString()).into(holder.binding.ivImage)
        with(holder.binding){
            btnSelect.setOnClickListener {view->
                savePhoto(image.toString())
            }
        }
    }

    private fun savePhoto(image: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        db.collection("users").document(uid).update("photoUrl",image).addOnSuccessListener {
            Log.e("", "savePhoto: saved photo", )
        }.addOnFailureListener {
            Log.e("", "savePhoto error : ${it.localizedMessage}", )

        }
    }

    fun updateList(newList : ArrayList<Uri>){
        imageList.clear()
        imageList.addAll(newList)
        notifyDataSetChanged()
    }

}