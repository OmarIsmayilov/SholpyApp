package com.example.sholpyapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sholpyapp.databinding.FragmentDetailBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var pId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product
        pId = product.id!!

        setFav(pId)
        setData(product)


    }

    private fun setFav(id: Int?) {
        val q = db.collection("wishlist").whereEqualTo("id", id)
        q.get().addOnSuccessListener { snapshot ->
            if (!snapshot.isEmpty) {
                binding.cbFav.isChecked = true
            }
        }
    }

    private fun removeFav(id: Int?) {
        db.collection("wishlist").document(id.toString()).delete()
    }

    private fun addFav(product: AllProductsResponseItem, id: Int?) {
        db.collection("wishlist").document(id.toString()).set(product)
    }

    private fun setData(product: AllProductsResponseItem) {
        with(binding) {
            ibBack.setOnClickListener { findNavController().popBackStack() }
            cbFav.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    addFav(product, pId)
                } else {
                    removeFav(pId)
                }
            }
            btnAddCart.setOnClickListener { addtoCart(product) }
            tvPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description
            tvProductName.text = product.title
            Picasso.get().load(product.image).into(ivProduct)
        }
    }

    private fun addtoCart(product: AllProductsResponseItem) {
        if (auth.currentUser!=null){
            db.collection("carts").document(auth.currentUser!!.uid)
                    .collection("products").document(product.id.toString()).set(product)
                    .addOnSuccessListener {
                        Toast.makeText(context,"Added to cart",Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener {
                        Toast.makeText(context,it.localizedMessage,Toast.LENGTH_SHORT).show() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}