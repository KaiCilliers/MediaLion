package com.sunrisekcdeveloper.medialion.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sunrisekcdeveloper.medialion.utils.FontRes

val Merriweather = FontFamily(
    Font(FontRes.primaryRegular.fontResourceId, FontWeight.Normal),
    Font(FontRes.primaryBoldItalic.fontResourceId, FontWeight.Bold, FontStyle.Italic),
    Font(FontRes.primaryItalic.fontResourceId, FontWeight.Normal, FontStyle.Italic),
    Font(FontRes.primaryLight.fontResourceId, FontWeight.Light, FontStyle.Normal),
    Font(FontRes.primaryLightItalic.fontResourceId, FontWeight.Light, FontStyle.Italic),
)

val typography = Typography(

    h1 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    h2 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),

    h3 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),

    subtitle2 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    body1 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp
    ),

    h4 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp

    ),

    body2 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    h5 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp

    )


    )