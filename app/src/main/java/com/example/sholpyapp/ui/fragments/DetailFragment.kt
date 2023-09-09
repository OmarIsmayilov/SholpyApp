package com.example.sholpyapp.ui.fragments

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentDetailBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.viewmodel.DetailViewModel
import com.example.sholpyapp.utils.Extensions.loadUrl
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by viewModels()
    private var model : AllProductsResponseItem?=null
    private val args: DetailFragmentArgs by navArgs()


    override fun observeEvents() {
        with(binding) {
            viewModel.productData.observe(viewLifecycleOwner) {
                it?.let {
                    product = it
                    model = it
                    ivProduct.loadUrl(it.image)
                }
            }
            viewModel.loading.observe(viewLifecycleOwner){
                binding.progressBar9.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateFinish() {
        val id = args.id
        viewModel.getProductById(id)


    }

    override fun setupListeners() {
        with(binding) {
            ibBack.setOnClickListener { findNavController().popBackStack() }
            cbFav.setOnCheckedChangeListener { _, b ->
                if (b){
                    viewModel.addToWishlist( WishlistItem(
                        model!!.id,
                        model!!.category!!,
                        model!!.description!!,
                        model!!.image!!,
                        model!!.price!!,
                        model!!.title!!,
                        model!!.quantity))
                }else{
                    viewModel.deleteFromWishlist( WishlistItem(
                        model!!.id,
                        model!!.category!!,
                        model!!.description!!,
                        model!!.image!!,
                        model!!.price!!,
                        model!!.title!!,
                        model!!.quantity))
                }
            }

            btnAddCart.setOnClickListener {
                model?.let {
                    val item = CartItem(
                        model!!.id,
                        model!!.category!!,
                        model!!.description!!,
                        model!!.image!!,
                        model!!.price!!,
                        model!!.title!!,
                        model!!.quantity
                    )
                    viewModel.addToCart(
                        item, "1"
                    )
                    Timber.tag(TAG).e("$item ")

                }
               }

        }

    }



}