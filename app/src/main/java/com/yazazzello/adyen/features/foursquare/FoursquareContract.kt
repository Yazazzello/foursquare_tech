package com.yazazzello.adyen.features.foursquare

import android.location.Location
import com.yazazzello.adyen.mvp.BasePresenter
import com.yazazzello.adyen.mvp.BaseView
import com.yazazzello.adyen.restapi.models.VenuesResponses


/**
 * Foursquare MVP Contract
 */
interface FoursquareContract {
    interface View : BaseView<Presenter> {
        fun flipProgress(isLoading: Boolean)
        fun displayVenues(list: List<VenuesResponses.Response.Venue>)
        fun showError(error: String)
        fun onPhotoUrlLoaded(photoUrl: String)
    }

    interface Presenter : BasePresenter<View> {
        fun loadObjects(latlon: Location, categoryId: String)
        fun loadPhoto(id: String) 
    }
}