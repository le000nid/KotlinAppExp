package com.example.appkotlin.di.module

import androidx.lifecycle.ViewModel
import com.example.appkotlin.activity.main.MainActivityViewModel
import com.example.appkotlin.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(mainActivityViewModel: MainActivityViewModel) : ViewModel
}