package com.rahulxiao.subscriptionmanagementapp.ui.theme

import androidx.compose.ui.graphics.Color

data class ThemeColors(
    val name: String,
    val primary: Color,
    val secondary: Color,
    val accentPeach: Color,
    val accentPink: Color,
    val gradientStart: Color,
    val gradientEnd: Color
)

object AppThemes {
    val DEFAULT = ThemeColors(
        name = "Default Purple",
        primary = Color(0xFF696FC7),
        secondary = Color(0xFFA7AAE1),
        accentPeach = Color(0xFFF5D3C4),
        accentPink = Color(0xFFF2AEBB),
        gradientStart = Color(0xFFA7AAE1),
        gradientEnd = Color(0xFF696FC7)
    )
    
    val OCEAN_BLUE = ThemeColors(
        name = "Ocean Blue",
        primary = Color(0xFF2196F3),
        secondary = Color(0xFF64B5F6),
        accentPeach = Color(0xFFFFE0B2),
        accentPink = Color(0xFFFFAB91),
        gradientStart = Color(0xFF64B5F6),
        gradientEnd = Color(0xFF1976D2)
    )
    
    val SUNSET_ORANGE = ThemeColors(
        name = "Sunset Orange",
        primary = Color(0xFFFF6F00),
        secondary = Color(0xFFFFB74D),
        accentPeach = Color(0xFFFFE0B2),
        accentPink = Color(0xFFFF8A80),
        gradientStart = Color(0xFFFFB74D),
        gradientEnd = Color(0xFFE65100)
    )
    
    val MINT_GREEN = ThemeColors(
        name = "Mint Green",
        primary = Color(0xFF26A69A),
        secondary = Color(0xFF80CBC4),
        accentPeach = Color(0xFFFFE0B2),
        accentPink = Color(0xFFCE93D8),
        gradientStart = Color(0xFF80CBC4),
        gradientEnd = Color(0xFF00897B)
    )
    
    val ROSE_PINK = ThemeColors(
        name = "Rose Pink",
        primary = Color(0xFFE91E63),
        secondary = Color(0xFFF48FB1),
        accentPeach = Color(0xFFFFCCBC),
        accentPink = Color(0xFFFF80AB),
        gradientStart = Color(0xFFF48FB1),
        gradientEnd = Color(0xFFC2185B)
    )
    
    val DARK_NIGHT = ThemeColors(
        name = "Dark Night",
        primary = Color(0xFF424242),
        secondary = Color(0xFF757575),
        accentPeach = Color(0xFFBDBDBD),
        accentPink = Color(0xFF9E9E9E),
        gradientStart = Color(0xFF424242),
        gradientEnd = Color(0xFF212121)
    )
    
    fun getAllThemes() = listOf(
        DEFAULT,
        OCEAN_BLUE,
        SUNSET_ORANGE,
        MINT_GREEN,
        ROSE_PINK,
        DARK_NIGHT
    )
    
    fun getThemeByName(name: String): ThemeColors {
        return getAllThemes().find { it.name == name } ?: DEFAULT
    }
}

