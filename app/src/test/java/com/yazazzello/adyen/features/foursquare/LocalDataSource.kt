package com.yazazzello.adyen.features.foursquare

import com.google.gson.GsonBuilder
import com.yazazzello.adyen.features.util.TestTools
import com.yazazzello.adyen.restapi.models.PhotosResponse
import com.yazazzello.adyen.restapi.models.VenuesResponses
import io.reactivex.Single
import java.lang.Exception

class LocalDataSource: FoursquareRepository {
    override fun getVenues(categoryId: String, latLon: String): Single<VenuesResponses> {
        return when (categoryId) {
            "good" -> Single.just(GsonBuilder().create().fromJson(TestTools.getStringFromFile("venues.json"),
                VenuesResponses::class.java))
            else -> Single.error(Exception("something bad"))
        }
    }

    override fun getPhotos(venueId: String): Single<PhotosResponse> {
        return when (venueId) {
            "good" -> Single.just(GsonBuilder().create().fromJson(TestTools.getStringFromFile("photos.json"),
                PhotosResponse::class.java))
            else -> Single.error(Exception("something bad"))
        }
    }
}