package com.yazazzello.adyen.features.foursquare

import android.util.SparseArray
import com.yazazzello.adyen.restapi.FoursquareApi
import com.yazazzello.adyen.restapi.models.PhotosResponse
import com.yazazzello.adyen.restapi.models.VenuesResponses
import io.reactivex.Maybe
import io.reactivex.Single

interface FoursquareRepository {
    fun getVenues(categoryId: String, latLon: String): Single<VenuesResponses>
    fun getPhotos(venueId: String): Single<PhotosResponse>
}

class FoursquareRepositoryImpl(
    private val foursquareApi: FoursquareApi,
    private val clientId: String,
    private val clientSecret: String
) : FoursquareRepository {
    private val cache: SparseArray<PhotosResponse> = SparseArray()

    override fun getVenues(categoryId: String, latLon: String): Single<VenuesResponses> {
        return foursquareApi.getVenues(categoryId, latLon, clientId, clientSecret)
    }

    override fun getPhotos(venueId: String): Single<PhotosResponse> {
        return Maybe
            .fromCallable { (cache.get(venueId.hashCode())) }
            .switchIfEmpty(foursquareApi.getPhotos(venueId, clientId, clientSecret)
                .doOnEvent { photoResponse, _ -> cache.put(venueId.hashCode(), photoResponse) })

    }
}