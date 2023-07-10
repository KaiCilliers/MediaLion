package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import kotlin.jvm.JvmInline

interface TitledMediaList {
    @JvmInline
    value class Def(private val value: List<MediaWithTitle>) : TitledMediaList
}