package com.yazazzello.adyen.restapi.models

import com.google.gson.annotations.SerializedName


data class PhotosResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("response") val response: Response
) {

    data class Meta(
        @SerializedName("code") val code: Int,
        @SerializedName("requestId") val requestId: String
    )


    data class Response(
        @SerializedName("photos") val photos: Photos
    ) {

        data class Photos(
            @SerializedName("count") val count: Int,
            @SerializedName("items") val items: List<Item>,
            @SerializedName("dupesRemoved") val dupesRemoved: Int
        ) {

            data class Item(
                @SerializedName("id") val id: String,
                @SerializedName("createdAt") val createdAt: Int,
                @SerializedName("source") val source: Source,
                @SerializedName("prefix") val prefix: String,
                @SerializedName("suffix") val suffix: String,
                @SerializedName("width") val width: Int,
                @SerializedName("height") val height: Int,
                @SerializedName("user") val user: User,
                @SerializedName("checkin") val checkin: Checkin,
                @SerializedName("visibility") val visibility: String
            ) {

                data class Source(
                    @SerializedName("name") val name: String,
                    @SerializedName("url") val url: String
                )


                data class User(
                    @SerializedName("id") val id: String,
                    @SerializedName("firstName") val firstName: String,
                    @SerializedName("lastName") val lastName: String,
                    @SerializedName("gender") val gender: String,
                    @SerializedName("photo") val photo: Photo
                ) {

                    data class Photo(
                        @SerializedName("prefix") val prefix: String,
                        @SerializedName("suffix") val suffix: String
                    )
                }


                data class Checkin(
                    @SerializedName("id") val id: String,
                    @SerializedName("createdAt") val createdAt: Int,
                    @SerializedName("type") val type: String,
                    @SerializedName("timeZoneOffset") val timeZoneOffset: Int
                )
            }
        }
    }
}