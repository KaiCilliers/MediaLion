package com.sunrisekcdeveloper.medialion.oldArch.domain.value

// todo make properties private & use interface
data class Title(val value: String) {
    override fun toString(): String {
        return value
    }
}