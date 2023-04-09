package com.example.medialion

import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.Raw
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

class SharedTextResource {
    init {
        StringDesc.localeType = StringDesc.LocaleType.Custom("ru")
        // back to system default
//        StringDesc.localeType = StringDesc.LocaleType.System
    }
    fun getGreetingLine() : StringDesc {
        return StringDesc.Resource(SharedRes.strings.greeting)
    }

    fun getGreetingWithName(name: String): StringDesc {
        return StringDesc.ResourceFormatted(
            SharedRes.strings.greeting_with_name, name
        )
    }

    fun getMyPluralFormattedDesc(quantity: Int) : StringDesc {
        // we pass quantity as selector for correct plural string and for pass quantity as argument for formatting
        return StringDesc.PluralFormatted(SharedRes.plurals.dress, quantity, quantity)
    }

    fun getUserName(user: String?): StringDesc {
        return if (user != null) {
            StringDesc.Raw(user)
        } else {
            StringDesc.Resource(SharedRes.strings.placeholder)
        }
    }

}