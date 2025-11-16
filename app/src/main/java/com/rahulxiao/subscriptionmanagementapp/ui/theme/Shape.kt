package com.rahulxiao.subscriptionmanagementapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Shape tokens from mockup spec
val AppShapes = Shapes(
    // Small: 12dp
    small = RoundedCornerShape(12.dp),
    
    // Medium: 18dp
    medium = RoundedCornerShape(18.dp),
    
    // Large: 24dp
    large = RoundedCornerShape(24.dp),
    
    // Extra large: 20dp (for hero card)
    extraLarge = RoundedCornerShape(20.dp)
)

// Additional custom corner radii
val CornerRadiusSmall = 12.dp
val CornerRadiusMedium = 18.dp
val CornerRadiusLarge = 24.dp
val CornerRadiusHero = 20.dp

