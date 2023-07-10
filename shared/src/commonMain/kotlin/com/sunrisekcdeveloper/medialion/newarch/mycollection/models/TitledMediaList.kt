package com.sunrisekcdeveloper.medialion.newarch.mycollection.models

import kotlin.jvm.JvmInline

interface TitledMediaList {
    @JvmInline
    value class Def(private val value: List<MediaWithTitle>) : TitledMediaList
}