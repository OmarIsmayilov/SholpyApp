package com.example.sholpyapp.ui.fragments

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.CartAdapter
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentCartBinding
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.sholpyapp.R.string.price


@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    private val cartAdapter = CartAdapter()
    private val viewModel: CartViewModel by viewModels()


    override fun observeEvents() {
        with(binding) {
            with(viewModel) {
                loading.observe(viewLifecycleOwner) {
                    progressBar3.visibility = if (it) View.VISIBLE else View.GONE
                }

                cartData.observe(viewLifecycleOwner) {
                    Log.e(TAG, "fragment result: ${it}", )
                    if (it.isNotEmpty()) {
                        rvCart.adapter = cartAdapter
                        cartAdapter.differ.submitList(it as ArrayList<CartItem>)
                    }
                    tvInfo.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
                }

                total.observe(viewLifecycleOwner){
                    tvTotal.text = getString(price, it)
                }

            }
        }
    }


    override fun onCreateFinish() {

    }

    override fun setupListeners() {
        with(binding){
            btnClear.setOnClickListener{
                viewModel.deleteAllData()
            }

            cartAdapter.onClickMinus = {
                viewModel.decrease(it)
            }

            cartAdapter.onClickPlus = {
                viewModel.increase(it)
            }
        }
    }


}





