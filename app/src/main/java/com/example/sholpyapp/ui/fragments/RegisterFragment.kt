package com.example.sholpyapp.ui.fragments



import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sholpyapp.base.BaseFragment
import com.example.sholpyapp.databinding.FragmentRegisterBinding
import com.example.sholpyapp.viewmodel.LoginViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun observeEvents() {
        with(binding) {
            with(viewModel) {
                loading.observe(viewLifecycleOwner) {
                    progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
                resultAuth.observe(viewLifecycleOwner) {
                    it?.let {
                        FancyToast.makeText(
                            requireContext(),
                            it.message,
                            FancyToast.LENGTH_SHORT,
                            if (it.success) FancyToast.SUCCESS else FancyToast.ERROR,
                            false
                        ).show()
                        if (it.success) {
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment())
                        }
                    }

                }
            }
        }
    }



    override fun onCreateFinish() {

    }

    override fun setupListeners() {
        with(binding) {
            tvSignIn.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment()) }
            btnSignUp.setOnClickListener {
                register()
            }
            ibBack.setOnClickListener { findNavController().popBackStack() }
            googleLogin.setOnClickListener { }
        }
    }


    private fun validateData(name: String?, email: String?, password: String?): Boolean {
        return when {
            name.isNullOrEmpty()-> false
            email.isNullOrEmpty()-> false
            password.isNullOrEmpty() -> false
            else -> {
                true
            }
        }
    }

    private fun register() {
        with(binding) {
            val name = etName.text.toString().trim()
            val email = etRegEmail.text.toString().trim()
            val password = etRegPass.text.toString().trim()

            if (validateData(name, email, password)) {
                viewModel.signUp(email, password)
                //savetodb(name,email,password)
            }else{
                FancyToast.makeText(
                    requireContext(),
                    "Fields cannot be empty",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    false
                ).show()
            }

        }


    }

    /*  private fun saveToDb() {
          uid = auth.currentUser!!.uid
          val timestamp = System.currentTimeMillis().toString()
          val user = User(name, email, uid, timestamp,null)
          db.collection("users").document(uid).set(user)
              .addOnSuccessListener {
                  dialog(false)
                  toast(" Create account succesfully")
                  findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment())
              }
              .addOnFailureListener {
                  dialog(false)
                  it.localizedMessage?.let { it1 -> toast(it1) } }
      }*/


}