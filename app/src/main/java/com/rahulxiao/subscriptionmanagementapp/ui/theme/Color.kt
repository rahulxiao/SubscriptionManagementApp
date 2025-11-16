package com.rahulxiao.subscriptionmanagementapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dynamic colors based on current theme
val Primary: Color
    @Composable get() = ThemeManager.currentTheme.primary

val Secondary: Color
    @Composable get() = ThemeManager.currentTheme.secondary

val AccentPeach: Color
    @Composable get() = ThemeManager.currentTheme.accentPeach

val AccentPink: Color
    @Composable get() = ThemeManager.currentTheme.accentPink

val GradientStart: Color
    @Composable get() = ThemeManager.currentTheme.gradientStart

val GradientEnd: Color
    @Composable get() = ThemeManager.currentTheme.gradientEnd

// Text Colors (static)
val TextPrimary = Color(0xFFF5F7FA)       // White-on-dark
val TextSecondary = Color(0xFFEDEFF8)     // Body text
val TextTertiary = Color(0xFFCFCFE8)      // Small/muted text

// Glass Morphism (static)
val GlassBackground = Color(0x1AFFFFFF)   // rgba(255,255,255,0.10)
val GlassBorder = Color(0x1FFFFFFF)       // rgba(255,255,255,0.12)

// Renewal Status Colors (dynamic)
val RenewalSafe: Color
    @Composable get() = ThemeManager.currentTheme.secondary

val RenewalNear: Color
    @Composable get() = ThemeManager.currentTheme.accentPeach

val RenewalAlert: Color
    @Composable get() = ThemeManager.currentTheme.accentPink

// Dark background (static)
val BackgroundDark = Color(0xFF1A1A1A)

