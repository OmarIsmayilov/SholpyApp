package com.example.sholpyapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentAccountBinding
import com.example.sholpyapp.model.User
import com.example.sholpyapp.utils.Extensions.loadUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {


    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    private lateinit var uid: String
    override fun observeEvents() {
    }

    override fun onCreateFinish() {
        getData()
    }

    override fun setupListeners() {
    }


    private fun getData() {
        uid = auth.currentUser?.uid.toString()
        with(binding) {
            ibBack.setOnClickListener { findNavController().popBackStack() }

            db.collection("users").document(uid).get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    setData(user)

                }

            ivProfil.setOnClickListener {
                findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToImagesFragment2())
            }
        }


    }


    private fun setData(user: User?) {
        with(binding) {
            etCName.setText(user?.name)
            etCEmail.setText(user?.email)
            etCNumber.setText(user?.phoneNumber)
            ivProfil.loadUrl(user?.photoUrl)
            lyName.setEndIconOnClickListener {
                etCName.isEnabled = true
                etCName.requestFocus()
            }
            lyNumber.setEndIconOnClickListener {
                etCNumber.isEnabled = true
                etCNumber.requestFocus()
            }

            btnSave.setOnClickListener {
                val nName = etCName.text.toString().trim()
                val nNumber = etCNumber.text.toString().trim()

                if (etCPass.visibility == View.VISIBLE) {
                    if (nName.isNotEmpty() && nNumber.isNotEmpty()) {
                        updateData(nName, nNumber)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Fill the empty fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (nName.isNotEmpty() && nNumber.isNotEmpty()) {
                        updateData(nName, nNumber)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Fill the empty fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
            tvChange.setOnClickListener {
                if (lyPass.visibility == View.GONE) {
                    lyPass.visibility = View.VISIBLE
                } else {
                    lyPass.visibility = View.GONE
                }
            }
            binding.ibBack.setOnClickListener { findNavController().popBackStack() }

        }
    }

    private fun updateData(nName: String, nMobile: String) {
        db.collection("users").document(uid).update("name", nName, "phoneNumber", nMobile)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Uptaded succesfully", Toast.LENGTH_SHORT).show()
                with(binding) {
                    etCName.isEnabled = false
                    etCNumber.isEnabled = false
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
            }


    }


}