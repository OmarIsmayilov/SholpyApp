package com.example.sholpyapp.ui.fragments

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.ProductsAdapter
import com.example.sholpyapp.adapter.RatedProductAdapter
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentHomeBinding
import com.example.sholpyapp.viewmodel.HomeViewModel
import com.example.sholpyapp.utils.Extensions.loadUrl
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CenterScrollListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel: HomeViewModel by viewModels()
    private val productAdapter = ProductsAdapter()
    private val ratedProductAdapter = RatedProductAdapter()


    override fun observeEvents() {
        with(binding) {
            viewModel.loading.observe(viewLifecycleOwner) {
                binding.progressBar2.visibility = if (it) View.VISIBLE else View.GONE
                binding.progressBar8.visibility = if (it) View.VISIBLE else View.GONE
            }
            viewModel.ratedProductData.observe(viewLifecycleOwner) {
                it?.let {
                    ratedProductAdapter.updateList(it)
                }

            }
            viewModel.allProductData.observe(viewLifecycleOwner) {
                it?.let {
                    productAdapter.differ.submitList(it)
                }
            }
            viewModel.userPhoto.observe(viewLifecycleOwner) {
                it?.let {
                    ivProfil.loadUrl(it)
                }
            }
            viewModel.theme.observe(viewLifecycleOwner) {
                AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateFinish() {
        setAdapter()
    }

    private fun setAdapter() {
        with(binding) {
            val layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
            rvRatedProducts.layoutManager = layoutManager
            rvRatedProducts.addOnScrollListener(CenterScrollListener())
            rvRatedProducts.adapter = ratedProductAdapter
            rvProducts.adapter = productAdapter
        }

    }

    override fun setupListeners() {
        with(binding) {
            tvShow.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFilterProductFragment()) }
            ivProfil.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment()) }
            ratedProductAdapter.onClick = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        it
                    )
                )
            }
            productAdapter.onClick = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        it
                    )
                )
            }
            val listener = View.OnClickListener { view ->
                when (view.id) {
                    R.id.ivMen -> transition("men's clothing")
                    R.id.ivWomen -> transition("women's clothing")
                    R.id.ivElectronics -> transition("electronics")
                    R.id.ivJewelery -> transition("jewelery")
                }
            }

            ivMen.setOnClickListener(listener)
            ivWomen.setOnClickListener(listener)
            ivElectronics.setOnClickListener(listener)
            ivJewelery.setOnClickListener(listener)

        }
    }



    private fun transition(category: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryProductFragment(
                category
            )
        )
    }


}