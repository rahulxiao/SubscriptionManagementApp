package com.rahulxiao.subscriptionmanagementapp.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Entrance animation with 240ms duration and ease-out-cubic
 */
fun Modifier.entranceAnimation(
    delay: Int = 0
): Modifier = composed {
    val animatedAlpha = remember { Animatable(0f) }
    val animatedTranslation = remember { Animatable(30f) }
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        launch {
            animatedAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 240,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            animatedTranslation.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 240,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }
    
    this
        .graphicsLayer {
            alpha = animatedAlpha.value
            translationY = animatedTranslation.value
        }
}

/**
 * Press animation - scales to 0.985 over 120ms
 */
fun Modifier.pressAnimation(
    pressed: Boolean
): Modifier = composed {
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.985f else 1f,
        animationSpec = tween(
            durationMillis = 120,
            easing = FastOutSlowInEasing
        ),
        label = "press_scale"
    )
    
    this.scale(scale)
}

/**
 * Breathing animation for FAB - scale 1.00 -> 1.03 over 3000ms
 */
@Composable
fun rememberBreathingAnimation(): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.00f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing_scale"
    )
    return scale
}

/**
 * Pulse animation for price badges - subtle x-scale 1.02 -> 0.98, 800ms loop
 */
@Composable
fun rememberPulseAnimation(): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.02f,
        targetValue = 0.98f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    return scale
}

/**
 * Stagger animation for lists - each item delayed by 80ms
 */
fun getStaggerDelay(index: Int): Int {
    return index * 80
}

