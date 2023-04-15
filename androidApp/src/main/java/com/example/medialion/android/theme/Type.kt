package com.example.medialion.android.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.medialion.SharedRes

val Merriweather = FontFamily(
    Font(SharedRes.fonts.Merriweather.regular.fontResourceId, FontWeight.Normal),
    Font(SharedRes.fonts.Merriweather.boldItalic.fontResourceId, FontWeight.Bold, FontStyle.Italic),
    Font(SharedRes.fonts.Merriweather.italic.fontResourceId, FontWeight.Normal, FontStyle.Italic),
    Font(SharedRes.fonts.Merriweather.light.fontResourceId, FontWeight.Light, FontStyle.Normal),
    Font(SharedRes.fonts.Merriweather.lightItalic.fontResourceId, FontWeight.Light, FontStyle.Italic),
)

val typography = Typography(

    h1 = TextStyle(
        fontFamily = Merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
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
        fontSize = 22.sp,
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


    )