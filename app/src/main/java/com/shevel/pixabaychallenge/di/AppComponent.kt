package com.shevel.pixabaychallenge.di

import android.content.Context
import com.shevel.pixabaychallenge.ui.MainActivity
import com.shevel.pixabaychallenge.ui.fragments.main.MainFragment
import com.shevel.pixabaychallenge.ui.fragments.main.MainViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}