package com.example.sholpyapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.databinding.FragmentAccountBinding
import com.example.sholpyapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    private lateinit var  uid : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        uid = auth.currentUser?.uid.toString()
        with(binding){
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
        with(binding){
            etCName.setText(user?.name)
            etCEmail.setText(user?.email)
            etCNumber.setText(user?.phoneNumber)
            Picasso.get().load(user?.photoUrl).into(ivProfil)
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

                if (etCPass.visibility == View.VISIBLE){
                    if (nName.isNotEmpty()  && nNumber.isNotEmpty()){
                        updateData(nName,nNumber)
                    }else{
                        Toast.makeText(requireContext(),"Fill the empty fields",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    if (nName.isNotEmpty() && nNumber.isNotEmpty()){
                        updateData(nName, nNumber)
                    }else{
                        Toast.makeText(requireContext(),"Fill the empty fields",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            tvChange.setOnClickListener {
                if (lyPass.visibility == View.GONE){
                    lyPass.visibility = View.VISIBLE
                }else{
                    lyPass.visibility = View.GONE
                }
            }
            binding.ibBack.setOnClickListener { findNavController().popBackStack() }

        }
    }

    private fun updateData(nName: String, nMobile: String) {
       db.collection("users").document(uid).update("name",nName,"phoneNumber",nMobile)
           .addOnSuccessListener {
                Toast.makeText(requireContext(),"Uptaded succesfully",Toast.LENGTH_SHORT).show()
               with(binding){
                   etCName.isEnabled = false
                   etCNumber.isEnabled = false
               }
           }
           .addOnFailureListener {
               Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
           }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}