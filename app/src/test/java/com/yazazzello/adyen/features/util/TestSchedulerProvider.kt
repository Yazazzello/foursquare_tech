package com.yazazzello.adyen.features.util

import com.yazazzello.adyen.util.rx.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}