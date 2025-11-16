package com.rahulxiao.subscriptionmanagementapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
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
import com.rahulxiao.subscriptionmanagementapp.data.model.getFormattedPrice
import com.rahulxiao.subscriptionmanagementapp.ui.theme.*
import com.rahulxiao.subscriptionmanagementapp.viewmodel.DetailsViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDetailsScreen(
    viewModel: DetailsViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val subscription = uiState.subscription
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    if (uiState.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary)
        }
        return
    }
    
    if (subscription == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Subscription not found",
                style = MaterialTheme.typography.displayMedium
            )
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = TextPrimary
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                )
                .padding(paddingValues)
        ) {
            // Large Header
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Primary, Secondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = subscription.serviceInitial,
                                style = MaterialTheme.typography.displayLarge,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = subscription.serviceName,
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )
                    }
                }
            }
            
            // Details Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Price and Billing Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        InfoChip(
                            label = "Price",
                            value = subscription.getFormattedPrice()
                        )
                        InfoChip(
                            label = "Billing",
                            value = subscription.billingCycle.displayName
                        )
                        InfoChip(
                            label = "Renews In",
                            value = "${subscription.getDaysUntilRenewal()}d"
                        )
                    }
                    
                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Edit */ },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(CornerRadiusSmall),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TextPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorder)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Edit")
                        }
                        
                        OutlinedButton(
                            onClick = {
                                viewModel.deleteSubscription(onDeleted = onNavigateBack)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(CornerRadiusSmall),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = AccentPink
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AccentPink)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cancel")
                        }
                    }
                    
                    // Details Card
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
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DetailRow("Category", subscription.category)
                            DetailRow("Renewal Date", dateFormat.format(Date(subscription.renewalDate)))
                            DetailRow("Auto Renew", if (subscription.autoRenew) "Yes" else "No")
                        }
                    }
                    
                    // Price History
                    Text(
                        text = "Price History",
                        style = MaterialTheme.typography.displayMedium
                    )
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(CornerRadiusMedium))
                            .background(GlassBackground)
                            .border(
                                width = 1.dp,
                                color = GlassBorder,
                                shape = RoundedCornerShape(CornerRadiusMedium)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No price changes",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextTertiary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextTertiary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary
        )
    }
}

