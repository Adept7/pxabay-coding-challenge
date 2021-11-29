package com.shevel.pixabaychallenge

import android.app.Application
import com.shevel.pixabaychallenge.di.AppComponent
import com.shevel.pixabaychallenge.di.DaggerAppComponent

class PixabayChallengeApp: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}