package com.yazazzello.adyen.features.di

import com.yazazzello.adyen.di.appModules
import com.yazazzello.adyen.features.foursquare.FoursquareRepository
import com.yazazzello.adyen.features.foursquare.LocalDataSource
import com.yazazzello.adyen.features.util.TestSchedulerProvider
import com.yazazzello.adyen.util.rx.SchedulerProvider
import org.koin.dsl.module.applicationContext


val localJavaDatasourceModule = applicationContext {
    bean { LocalDataSource() as FoursquareRepository }
}

val testRxModule = applicationContext {
    // provided components
    bean { TestSchedulerProvider() as SchedulerProvider }
}

val testApp = appModules + testRxModule + localJavaDatasourceModule