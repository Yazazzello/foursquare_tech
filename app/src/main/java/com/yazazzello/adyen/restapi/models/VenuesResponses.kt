package com.yazazzello.adyen.restapi.models

import com.google.gson.annotations.SerializedName


data class VenuesResponses(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("response") val response: Response
) {

    data class Meta(
        @SerializedName("code") val code: Int,
        @SerializedName("requestId") val requestId: String
    )


    data class Response(
        @SerializedName("venues") val venues: List<Venue>,
        @SerializedName("confident") val confident: Boolean
    ) {

        data class Venue(
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("location") val location: Location,
            @SerializedName("categories") val categories: List<Category>,
            @SerializedName("verified") val verified: Boolean,
            @SerializedName("stats") val stats: Stats,
            @SerializedName("beenHere") val beenHere: BeenHere,
            @SerializedName("hereNow") val hereNow: HereNow,
            @SerializedName("referralId") val referralId: String,
            @SerializedName("venueChains") val venueChains: List<Any>,
            @SerializedName("hasPerk") val hasPerk: Boolean
        ) {

            data class Stats(
                @SerializedName("tipCount") val tipCount: Int,
                @SerializedName("usersCount") val usersCount: Int,
                @SerializedName("checkinsCount") val checkinsCount: Int,
                @SerializedName("visitsCount") val visitsCount: Int
            )


            data class Category(
                @SerializedName("id") val id: String,
                @SerializedName("name") val name: String,
                @SerializedName("pluralName") val pluralName: String,
                @SerializedName("shortName") val shortName: String,
                @SerializedName("icon") val icon: Icon,
                @SerializedName("primary") val primary: Boolean
            ) {

                data class Icon(
                    @SerializedName("prefix") val prefix: String,
                    @SerializedName("suffix") val suffix: String
                )
            }


            data class Location(
                @SerializedName("address") val address: String,
                @SerializedName("lat") val lat: Double,
                @SerializedName("lng") val lng: Double,
                @SerializedName("labeledLatLngs") val labeledLatLngs: List<LabeledLatLng>,
                @SerializedName("distance") val distance: Int,
                @SerializedName("postalCode") val postalCode: String,
                @SerializedName("cc") val cc: String,
                @SerializedName("neighborhood") val neighborhood: String,
                @SerializedName("city") val city: String,
                @SerializedName("state") val state: String,
                @SerializedName("country") val country: String,
                @SerializedName("formattedAddress") val formattedAddress: List<String>
            ) {

                data class LabeledLatLng(
                    @SerializedName("label") val label: String,
                    @SerializedName("lat") val lat: Double,
                    @SerializedName("lng") val lng: Double
                )
            }


            data class HereNow(
                @SerializedName("count") val count: Int,
                @SerializedName("summary") val summary: String,
                @SerializedName("groups") val groups: List<Any>
            )


            data class BeenHere(
                @SerializedName("count") val count: Int,
                @SerializedName("lastCheckinExpiredAt") val lastCheckinExpiredAt: Int,
                @SerializedName("marked") val marked: Boolean,
                @SerializedName("unconfirmedCount") val unconfirmedCount: Int
            )
        }
    }
}