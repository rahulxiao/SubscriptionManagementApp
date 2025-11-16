package com.rahulxiao.subscriptionmanagementapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SubscriptionManagementAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val currentTheme = ThemeManager.currentTheme
    
    val colorScheme = darkColorScheme(
        primary = currentTheme.primary,
        secondary = currentTheme.secondary,
        tertiary = currentTheme.accentPeach,
        background = BackgroundDark,
        surface = GlassBackground,
        onPrimary = TextPrimary,
        onSecondary = Color(0xFF1A1A1A),
        onTertiary = Color(0xFF1A1A1A),
        onBackground = TextPrimary,
        onSurface = TextPrimary,
        error = currentTheme.accentPink,
        onError = Color(0xFF1A1A1A)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

