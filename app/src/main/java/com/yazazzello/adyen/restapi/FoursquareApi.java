package com.yazazzello.adyen.restapi;

import com.yazazzello.adyen.restapi.models.PhotosResponse;
import com.yazazzello.adyen.restapi.models.VenuesResponses;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoursquareApi {

    @GET("venues/search?limit=10&v=20180323&radius=10000")
    Single<VenuesResponses> getVenues(
            @Query("categoryId") String categoryId,
            @Query("ll") String latlon,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret);

    @GET("venues/{venueId}/photos?v=20180323")
    Single<PhotosResponse> getPhotos(
            @Path("venueId") String venueId,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret);
}
