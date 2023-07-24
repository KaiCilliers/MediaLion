package com.sunrisekcdeveloper.medialion.android.features.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class HomeDestinations(val name: String): Parcelable {
    @Parcelize
    data object Discovery : HomeDestinations("Discovery")
    @Parcelize
    data object Collections : HomeDestinations("Collections")
}