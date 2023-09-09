package com.example.sholpyapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.sholpyapp.model.response.ResponseModel
import com.google.android.play.core.integrity.e
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(val auth: FirebaseAuth){

    private val authModel = MutableLiveData<ResponseModel>()

    fun createUser(email:String,password:String) : ResponseModel?{
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                authModel.postValue(ResponseModel("Create account successful",true))
            }.addOnFailureListener {
                authModel.postValue(it.localizedMessage?.let { it1 -> ResponseModel(it1,false) })
            }
            return authModel.value
    }


    fun loginUser(email: String, password: String) : ResponseModel? {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                authModel.postValue(ResponseModel("Login Succesful",true))
            }
            .addOnFailureListener {
                authModel.postValue(it.localizedMessage?.let { it1 -> ResponseModel(it1,false) })
            }
        return authModel.value

    }

    fun exitUser() : ResponseModel?{
        return try {
            auth.signOut()
            authModel.postValue(ResponseModel("You have been logged out",true))
            authModel.value
        }catch (e:Exception){
            authModel.postValue(ResponseModel(e.message.toString(),false))
            authModel.value
        }
    }
}