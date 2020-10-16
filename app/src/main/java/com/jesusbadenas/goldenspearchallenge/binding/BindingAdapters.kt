package com.jesusbadenas.goldenspearchallenge.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.di.GlideApp

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("trackName", "trackDuration")
fun TextView.setTrackInfo(name: String?, duration: Long?) {
    text = resources.getString(R.string.track_info, name, duration)
}
