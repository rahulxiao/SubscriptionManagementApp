package com.rahulxiao.subscriptionmanagementapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.data.model.getDaysUntilRenewal
import com.rahulxiao.subscriptionmanagementapp.ui.components.AppBottomNavigation
import com.rahulxiao.subscriptionmanagementapp.ui.components.AppTopBar
import com.rahulxiao.subscriptionmanagementapp.ui.theme.*
import com.rahulxiao.subscriptionmanagementapp.viewmodel.AnalyticsViewModel
import com.rahulxiao.subscriptionmanagementapp.viewmodel.Timeframe

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel,
    onNavigateToDashboard: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToNotes: () -> Unit = {},
    onNavigateToSettings: () -> Unit,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val timeframe by viewModel.timeframe.collectAsState()
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            Spacer(modifier = Modifier.height(32.dp))
            AppTopBar(
                title = "Analytics",
                avatarInitial = "U",
                onAvatarClick = onNavigateToSettings,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    // Timeframe selector
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Timeframe.entries.forEach { tf ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .clip(RoundedCornerShape(CornerRadiusSmall))
                                        .background(
                                            if (timeframe == tf) Primary
                                            else GlassBackground
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (timeframe == tf) Primary else GlassBorder,
                                            shape = RoundedCornerShape(CornerRadiusSmall)
                                        )
                                        .clickable { viewModel.setTimeframe(tf) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = tf.displayName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (timeframe == tf) Color.White else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                    
                    // Stats Cards
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                label = "Monthly",
                                value = "৳${String.format("%.0f", uiState.monthlyTotal)}",
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                label = "Yearly",
                                value = "৳${String.format("%.0f", uiState.yearlyTotal)}",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    item {
                        StatCard(
                            label = "Biggest Expense",
                            value = uiState.biggestExpense?.let {
                                "${it.serviceName} - ৳${String.format("%.0f", it.price)}"
                            } ?: "None",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    
                    // Monthly Trend Chart
                    item {
                        Column {
                            Text(
                                text = "Monthly Trend",
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(CornerRadiusMedium))
                                    .background(GlassBackground)
                                    .border(
                                        width = 1.dp,
                                        color = GlassBorder,
                                        shape = RoundedCornerShape(CornerRadiusMedium)
                                    )
                                    .padding(16.dp)
                            ) {
                                // Simple bar chart representation
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    uiState.monthlyTrend.forEach { point ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            val maxValue = uiState.monthlyTrend.maxOfOrNull { it.amount } ?: 1.0
                                            val heightRatio = (point.amount / maxValue).toFloat()
                                            
                                            Box(
                                                modifier = Modifier
                                                    .width(32.dp)
                                                    .height((100 * heightRatio).dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(AccentPink)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = point.month,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // Category Breakdown
                    item {
                        Column {
                            Text(
                                text = "By Category",
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(CornerRadiusMedium))
                                    .background(GlassBackground)
                                    .border(
                                        width = 1.dp,
                                        color = GlassBorder,
                                        shape = RoundedCornerShape(CornerRadiusMedium)
                                    )
                                    .padding(16.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    uiState.categoryBreakdown.forEach { (category, amount) ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = category,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = "৳${String.format("%.0f", amount)}",
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // Upcoming Renewals
                    item {
                        Column {
                            Text(
                                text = "Upcoming Renewals",
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    
                    items(uiState.upcomingRenewals.take(5)) { subscription ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(CornerRadiusSmall))
                                .background(GlassBackground)
                                .border(
                                    width = 1.dp,
                                    color = GlassBorder,
                                    shape = RoundedCornerShape(CornerRadiusSmall)
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = subscription.serviceInitial,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = subscription.serviceName,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "in ${subscription.getDaysUntilRenewal()} days",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            
                            Text(
                                text = "৳${String.format("%.0f", subscription.price)}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
        
        // Bottom Navigation
        AppBottomNavigation(
            currentRoute = currentRoute,
            onNavigate = { route ->
                when (route) {
                    "dashboard" -> onNavigateToDashboard()
                    "notes" -> onNavigateToNotes()
                    "settings" -> onNavigateToSettings()
                }
            },
            onAddClick = onNavigateToAdd,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(CornerRadiusMedium))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(CornerRadiusMedium)
            )
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

