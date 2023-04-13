package com.example.sholpyapp.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sholpyapp.R
import com.example.sholpyapp.adapter.ImageAdapter
import com.example.sholpyapp.databinding.FragmentAccountBinding
import com.example.sholpyapp.databinding.FragmentImagesBinding
import com.google.android.gms.tasks.Tasks
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class ImagesFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!
    private val storage = Firebase.storage.reference
    private val iAdapter = ImageAdapter()
    private lateinit var imageList : ArrayList<Uri>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentImagesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList = arrayListOf()
        dialog(true)
        getImageList()
    }

    private fun getImageList() {
        binding.btnSelect.setOnClickListener {
            findNavController().navigate(ImagesFragmentDirections.actionImagesFragment2ToAccountFragment())
        }
        storage.child("images/").listAll().addOnSuccessListener { listResult ->
            val downloadUrlTasks = listResult.items.map { item -> item.downloadUrl }
            Tasks.whenAllSuccess<Uri>(downloadUrlTasks)
                .addOnSuccessListener { imageList ->
                    val rvImage = view?.findViewById<RecyclerView>(R.id.rvImage)
                    rvImage?.adapter = iAdapter
                    setAdapter(ArrayList(imageList))
                }.addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialog(bool: Boolean) {
        val pb = binding.progressBar7
        if (bool) {
            pb.visibility = View.VISIBLE
        } else {
            pb.visibility = View.INVISIBLE
        }
    }

    private fun setAdapter(imageList: ArrayList<Uri>) {
        iAdapter.updateList(imageList)
        dialog(false)
    }
}