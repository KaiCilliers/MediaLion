package com.example.medialion.android.ui.search

import androidx.fragment.app.Fragment
import com.example.medialion.android.app.BaseKey
import kotlinx.parcelize.Parcelize

@Parcelize
data object SearchKey : BaseKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
}