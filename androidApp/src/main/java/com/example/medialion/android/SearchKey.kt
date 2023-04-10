package com.example.medialion.android

import androidx.fragment.app.Fragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.parcelize.Parcelize

@Parcelize
data object SearchKey : BaseKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
}