package com.example.sholpyapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding as VB

    protected abstract fun observeEvents()
    protected abstract fun onCreateFinish()
    protected abstract fun setupListeners()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater)
        if (_binding == null) {
            throw NullPointerException("Binding is null")
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateFinish()
        observeEvents()
        setupListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}