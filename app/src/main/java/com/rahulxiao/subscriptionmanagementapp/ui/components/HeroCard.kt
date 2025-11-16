package com.rahulxiao.subscriptionmanagementapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GradientEnd
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GradientStart
import com.rahulxiao.subscriptionmanagementapp.ui.theme.CornerRadiusHero

@Composable
fun HeroCard(
    totalAmount: String,
    nextRenewal: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(CornerRadiusHero))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = "Monthly Total",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = totalAmount,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nextRenewal,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

