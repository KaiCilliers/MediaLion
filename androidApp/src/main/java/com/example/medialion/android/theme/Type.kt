package com.example.medialion.android.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.medialion.SharedRes

val Quicksand = FontFamily(
    Font(SharedRes.fonts.Quicksand.regular.fontResourceId, FontWeight.Normal),
    Font(SharedRes.fonts.Quicksand.bold.fontResourceId, FontWeight.Bold),
    Font(SharedRes.fonts.Quicksand.boldItalic.fontResourceId, FontWeight.Bold, FontStyle.Italic),
    Font(SharedRes.fonts.Quicksand.italic.fontResourceId, FontWeight.Normal, FontStyle.Italic),
    Font(SharedRes.fonts.Quicksand.light.fontResourceId, FontWeight.Light, FontStyle.Normal),
    Font(SharedRes.fonts.Quicksand.lightItalic.fontResourceId, FontWeight.Light, FontStyle.Italic),
)

val typography = Typography(

    h1 = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    ),

    h2 = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 30.sp
    ),

    h3 = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
    ),

    subtitle2 = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
    ),

    body1 = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp
    ),


    )