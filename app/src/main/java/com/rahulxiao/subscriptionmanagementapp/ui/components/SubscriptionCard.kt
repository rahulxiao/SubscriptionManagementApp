package com.rahulxiao.subscriptionmanagementapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.ui.animations.pressAnimation
import com.rahulxiao.subscriptionmanagementapp.ui.theme.*

enum class RenewalStatus {
    SAFE, NEAR, ALERT
}

@Composable
fun SubscriptionCard(
    serviceName: String,
    serviceInitial: String,
    lastUsed: String,
    price: String,
    renewalDays: String,
    renewalStatus: RenewalStatus,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
            .pressAnimation(isPressed)
            .clip(RoundedCornerShape(CornerRadiusMedium))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(CornerRadiusMedium)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar circle
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = serviceInitial,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Middle section
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = serviceName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lastUsed,
                style = MaterialTheme.typography.bodySmall
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // Right section
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = price,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            RenewalPill(
                days = renewalDays,
                status = renewalStatus
            )
        }
    }
}

@Composable
fun RenewalPill(
    days: String,
    status: RenewalStatus,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        RenewalStatus.SAFE -> RenewalSafe
        RenewalStatus.NEAR -> RenewalNear
        RenewalStatus.ALERT -> RenewalAlert
    }
    
    Box(
        modifier = modifier
            .height(28.dp)
            .clip(RoundedCornerShape(CornerRadiusSmall))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = days,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF1A1A1A)
        )
    }
}

