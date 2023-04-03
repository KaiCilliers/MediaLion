package com.example.medialion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform