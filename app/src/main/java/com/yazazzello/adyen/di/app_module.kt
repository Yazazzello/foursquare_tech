package com.yazazzello.adyen.di

import com.yazazzello.adyen.di.Params.FOURSQUARE_VIEW
import com.yazazzello.adyen.features.foursquare.FoursquareContract
import com.yazazzello.adyen.features.foursquare.FoursquarePresenter
import com.yazazzello.adyen.features.foursquare.FoursquareRepository
import com.yazazzello.adyen.features.foursquare.FoursquareRepositoryImpl
import com.yazazzello.adyen.util.rx.ApplicationSchedulerProvider
import com.yazazzello.adyen.util.rx.SchedulerProvider
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {

    // Presenter for Foursquare View
    factory { params -> FoursquarePresenter(get(), get(), params[FOURSQUARE_VIEW]) as FoursquareContract.Presenter }

    // Foursquare Data Repository
    bean {
        FoursquareRepositoryImpl(
            get(),
            getProperty(DatasourceProperties.CLIENT_ID),
            getProperty(DatasourceProperties.CLIENT_SECRET)
            ) as FoursquareRepository
    }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object Params {
    const val FOURSQUARE_VIEW = "FOURSQUARE_VIEW"
}

// Gather all app modules
val appModules = listOf(mainModule, rxModule)
