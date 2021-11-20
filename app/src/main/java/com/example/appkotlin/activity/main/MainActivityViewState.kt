package com.example.appkotlin.activity.main

import com.example.appkotlin.Users

sealed class MainActivityViewState {
    object ShowLoading : MainActivityViewState()
    class ShowError(val error: Throwable) : MainActivityViewState()
    class ShowData(val data: List<Users>) : MainActivityViewState()
}