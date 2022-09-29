package com.teguh.githubuserfinal.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @BindingAdapter("showImage")
    @JvmStatic
    fun showImage(imgView: ImageView, url: String?){
        Glide.with(imgView.context)
            .load(url)
            .into(imgView)
    }
}