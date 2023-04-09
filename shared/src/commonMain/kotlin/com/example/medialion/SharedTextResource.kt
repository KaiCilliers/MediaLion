package com.example.medialion

import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

class SharedTextResource {
    fun getGreetingLine() : StringDesc {
        return StringDesc.Resource(SharedRes.strings.greeting)
    }

    fun getGreetingWithName(name: String): StringDesc {
        return StringDesc.ResourceFormatted(
            SharedRes.strings.greeting_with_name, name
        )
    }

    fun getMyPluralFormattedDesc(quantity: Int) : StringDesc {
        //we pass quantity once as a selector to get associated
        //plural and once again to get the interpolated string.
        return StringDesc.PluralFormatted(SharedRes.plurals.dress, quantity, quantity)
    }

}