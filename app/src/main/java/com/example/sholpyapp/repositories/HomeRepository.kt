package com.example.sholpyapp.repositories

import com.example.sholpyapp.network.ProductDao
import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.model.User
import com.example.sholpyapp.utils.Constants.LIMIT
import com.example.sholpyapp.utils.Constants.SORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    val dao: ProductDao,
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
) {

    suspend fun getRatedProducts(): AllProductsResponse? {
        return withContext(Dispatchers.IO) {
            val response = dao.getRatedProducts(5, SORT)
            if (response.isSuccessful) {
                response.body()
            } else {
                throw Exception("An error occurred while fetching rated products.")
            }
        }
    }

    suspend fun getAllProducts(): AllProductsResponse? =
        withContext(Dispatchers.IO) {
            val response = dao.getAllProducts(LIMIT, SORT)
            if (response.isSuccessful) {
                response.body()
            } else {
                throw Exception("An error occurred while fetching products.")
            }
        }


    suspend fun getUserData() =
        withContext(Dispatchers.IO) {
            val uid = auth.currentUser?.uid
            val documentSnapshot = db.collection("users").document(uid.toString()).get().await()
            val user = documentSnapshot.toObject(User::class.java)
            return@withContext user?.photoUrl

        }


}

