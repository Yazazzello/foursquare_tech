package com.yazazzello.adyen

import android.app.Application
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import com.yazazzello.adyen.di.appModules
import com.yazazzello.adyen.di.networkModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class AdyenApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(appModules + networkModule)
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
            Timber.plant(Timber.DebugTree())
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectFileUriExposure()
                    .detectActivityLeaks()
                    .penaltyLog()
                    .build())
        }
    }
}