package com.yazazzello.adyen.util.ext

import io.reactivex.Single
import com.yazazzello.adyen.util.rx.SchedulerProvider

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
    this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())