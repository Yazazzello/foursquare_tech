package com.yazazzello.adyen.features.foursquare

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

interface VenueListener {
    fun onVenueSelected(venueId: String)
}

@BindingAdapter("venueListener", "venueTypes")
fun LinearLayout.bindButtons(venueListener: VenueListener, venueTypes: List<VenueButtonViewModel>) {
    if (venueTypes.size != 3) {
        throw IllegalArgumentException("wrong amount of venuetypes")
    }
    val clearSelection = {
        for (i in 0 until childCount) {
            getChildAt(i).isSelected = false
        }
    }
    venueTypes.forEachIndexed { index, venueButtonViewModel ->
        val textView = getChildAt(index) as TextView
        textView.text = venueButtonViewModel.title
        textView.setOnClickListener {
            if (it.isSelected) return@setOnClickListener
            venueListener.onVenueSelected(venueButtonViewModel.categoryId)
            clearSelection()
            it.isSelected = true
        }
    }
    getChildAt(0).callOnClick()
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl == null) {
        this.setImageResource(0)
        return
    }
    if (imageUrl.isNotEmpty())
        Picasso.with(this.context)
            .load(imageUrl)
            .centerCrop()
            .fit().into(this)
}