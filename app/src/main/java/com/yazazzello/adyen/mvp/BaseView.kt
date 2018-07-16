package com.yazazzello.adyen.mvp

interface BaseView<out T : BasePresenter<*>> {

    val presenter: T

}