package com.rahulxiao.subscriptionmanagementapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.ui.animations.rememberBreathingAnimation
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GradientEnd
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GradientStart

@Composable
fun GradientFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val breathingScale = rememberBreathingAnimation()
    
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .scale(breathingScale)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                ambientColor = GradientStart,
                spotColor = GradientStart
            ),
        containerColor = Color.Transparent,
        contentColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(GradientEnd, GradientStart)
                    )
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Subscription",
                modifier = Modifier
                    .size(28.dp)
                    .align(androidx.compose.ui.Alignment.Center),
                tint = Color.White
            )
        }
    }
}

