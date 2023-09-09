package com.example.sholpyapp.ui.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.adapter.SwipeHelper
import com.example.sholpyapp.adapter.WishlistAdapter
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentWishlistBinding
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding>(FragmentWishlistBinding::inflate) {

    private var wishlistAdapter = WishlistAdapter()
    private val viewModel: WishlistViewModel by viewModels()
    override fun observeEvents() {
        with(binding) {
            with(viewModel) {
                loading.observe(viewLifecycleOwner) {
                    progressBar4.visibility = if (it) View.VISIBLE else View.GONE
                }
                wishlistData.observe(viewLifecycleOwner) {
                    tvError.visibility = if (it.isEmpty())  View.VISIBLE else View.INVISIBLE
                    rvWish.adapter = wishlistAdapter
                    wishlistAdapter.differ.submitList(it as ArrayList<WishlistItem>)
                }

            }
        }
    }

    override fun onCreateFinish() {

    }

    override fun setupListeners() {
        binding.btnClearAll.setOnClickListener { viewModel.deleteAllData() }

        val swipeHelper = object : SwipeHelper() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {


                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeHelper)
        touchHelper.attachToRecyclerView(binding.rvWish)
    }


}



