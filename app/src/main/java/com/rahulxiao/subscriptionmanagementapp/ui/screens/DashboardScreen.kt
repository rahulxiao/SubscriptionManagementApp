package com.rahulxiao.subscriptionmanagementapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.data.model.getDaysUntilRenewal
import com.rahulxiao.subscriptionmanagementapp.data.model.getFormattedPrice
import com.rahulxiao.subscriptionmanagementapp.data.model.getLastUsedText
import com.rahulxiao.subscriptionmanagementapp.ui.animations.entranceAnimation
import com.rahulxiao.subscriptionmanagementapp.ui.animations.getStaggerDelay
import com.rahulxiao.subscriptionmanagementapp.ui.components.*
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GradientEnd
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GradientStart
import com.rahulxiao.subscriptionmanagementapp.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToDetails: (Long) -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToNotes: () -> Unit = {},
    onNavigateToSettings: () -> Unit,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
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
                title = "Subscriptions",
                avatarInitial = "U",
                onAvatarClick = onNavigateToSettings,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                // Content
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    // Hero Card
                    item {
                        HeroCard(
                            totalAmount = "৳${String.format("%.0f", uiState.monthlyTotal)} / month",
                            nextRenewal = uiState.nextRenewal
                        )
                    }
                    
                    // Section Header
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Active Subscriptions",
                                style = MaterialTheme.typography.displayMedium
                            )
                            Text(
                                text = "${uiState.subscriptions.size}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    
                    // Subscription List
                    if (uiState.subscriptions.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No subscriptions yet.\nTap + to add one!",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            }
                        }
                    } else {
                        itemsIndexed(
                            items = uiState.subscriptions,
                            key = { _, item -> item.id }
                        ) { index, subscription ->
                            val daysUntilRenewal = subscription.getDaysUntilRenewal()
                            val status = when {
                                daysUntilRenewal <= 3 -> RenewalStatus.ALERT
                                daysUntilRenewal <= 7 -> RenewalStatus.NEAR
                                else -> RenewalStatus.SAFE
                            }
                            
                            SubscriptionCard(
                                serviceName = subscription.serviceName,
                                serviceInitial = subscription.serviceInitial,
                                lastUsed = subscription.getLastUsedText(),
                                price = subscription.getFormattedPrice(),
                                renewalDays = "${daysUntilRenewal}d",
                                renewalStatus = status,
                                onClick = { onNavigateToDetails(subscription.id) },
                                modifier = Modifier.entranceAnimation(delay = getStaggerDelay(index))
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
                    "analytics" -> onNavigateToAnalytics()
                    "notes" -> onNavigateToNotes()
                    "settings" -> onNavigateToSettings()
                }
            },
            onAddClick = onNavigateToAdd,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

