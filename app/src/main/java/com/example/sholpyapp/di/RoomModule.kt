package com.example.sholpyapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sholpyapp.database.CartDao
import com.example.sholpyapp.database.RoomDB
import com.example.sholpyapp.database.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context) =
         Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "wishlist_table"
        ).build()


    @Provides
    @Singleton
    fun provideCartDao(db:RoomDB):CartDao=
        db.getCartDao()


    @Provides
    @Singleton
    fun provideWishlistDao(db:RoomDB):WishlistDao=
         db.getWishlistDao()


}