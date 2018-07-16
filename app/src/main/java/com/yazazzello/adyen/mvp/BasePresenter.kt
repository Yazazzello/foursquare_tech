package com.yazazzello.adyen.mvp

interface BasePresenter<T> {

    fun stop()

    var view: T
}