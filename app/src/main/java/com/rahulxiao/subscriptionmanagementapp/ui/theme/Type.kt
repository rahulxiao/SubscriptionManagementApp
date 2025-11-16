package com.rahulxiao.subscriptionmanagementapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system default font family
// TODO: Download Poppins fonts from Google Fonts and add TTF files to res/font/
// Then uncomment and use: FontFamily(Font(R.font.poppins_regular, FontWeight.Normal), etc.)
val PoppinsFontFamily = FontFamily.Default

// Static color for typography (doesn't change with theme)
private val StaticAccentPeach = Color(0xFFF5D3C4)

// Typography Scale based on mockup specs
val AppTypography = Typography(
    // H1: 28sp, 600, #F5F7FA
    displayLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        color = TextPrimary
    ),
    
    // H2: 20sp, 600, #F5F7FA
    displayMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = TextPrimary
    ),
    
    // Body: 16sp, 400, #EDEFF8
    bodyLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextSecondary
    ),
    
    bodyMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextSecondary
    ),
    
    // Small: 12-14sp, 400, #CFCFE8
    bodySmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextTertiary
    ),
    
    // Price / Emphasis: 18sp, 600, #F5D3C4 or #F2AEBB
    titleLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = StaticAccentPeach
    ),
    
    titleMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = TextPrimary
    ),
    
    titleSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = TextPrimary
    ),
    
    labelLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = TextSecondary
    ),
    
    labelMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextTertiary
    )
)

