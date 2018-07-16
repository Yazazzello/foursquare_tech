package com.yazazzello.adyen.features.foursquare

import android.location.Location
import com.yazazzello.adyen.mvp.AbstractPresenter
import com.yazazzello.adyen.util.ext.with
import com.yazazzello.adyen.util.rx.SchedulerProvider
import timber.log.Timber


class FoursquarePresenter(
    private val foursquareRepository: FoursquareRepository,
    private val schedulerProvider: SchedulerProvider,
    override var view: FoursquareContract.View
) : AbstractPresenter<FoursquareContract.View, FoursquareContract.Presenter>(),
    FoursquareContract.Presenter {

    override fun loadPhoto(id: String) {
        launch {
            foursquareRepository.getPhotos(id)
                .with(schedulerProvider)
                .doOnSubscribe {
                    view.flipProgress(true)
                }
                .doFinally {
                    view.flipProgress(false)
                }
                .map { photoResponse ->
                    val item = photoResponse.response.photos.items.firstOrNull()
                    item ?: throw IllegalArgumentException("no photos")
                    item.prefix + "500x500" + item.suffix
                }
                .subscribe({
                    view.onPhotoUrlLoaded(it)
                }, { error ->
                    Timber.e(error)
                    view.showError(error.message ?: "Error")
                })
        }
    }

    override fun loadObjects(latlon: Location, categoryId: String) {
        val locationStr = String.format(
            "%1\$s,%2\$s",
            latlon.latitude.toString(),
            latlon.longitude.toString()
        )
        launch {
            foursquareRepository.getVenues(categoryId, locationStr)
                .with(schedulerProvider)
                .doOnSubscribe {
                    view.flipProgress(true)
                }
                .doFinally {
                    view.flipProgress(false)
                }
                .map { foursquareResponse ->
                    foursquareResponse.response.venues ?: emptyList()
                }
                .subscribe({
                    view.displayVenues(it)
                }, { error ->
                    Timber.e(error)
                    view.showError(error.message ?: "Error")
                })
        }
    }
}