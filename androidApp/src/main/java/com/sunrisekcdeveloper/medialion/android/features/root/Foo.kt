package com.sunrisekcdeveloper.medialion.android.features.root

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Foo(
    val s: (String) -> Unit = {}
) : Parcelable