package com.example.sholpyapp.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.sholpyapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shashank.sony.fancytoastlib.FancyToast

object Extensions {

    fun ImageView.loadUrl(url: String?) {
        url?.let {
            Glide
                .with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.et_bg)
                .into(this)
        }
    }



    fun View.visible(){
        this.visibility = View.VISIBLE
    }

    fun View.gone(){
        this.visibility = View.GONE
    }

    fun View.invisible(){
        this.visibility = View.INVISIBLE
    }

}