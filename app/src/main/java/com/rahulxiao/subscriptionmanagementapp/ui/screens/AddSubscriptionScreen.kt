package com.rahulxiao.subscriptionmanagementapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.data.model.BillingCycle
import com.rahulxiao.subscriptionmanagementapp.ui.theme.*
import com.rahulxiao.subscriptionmanagementapp.viewmodel.AddSubscriptionViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionScreen(
    viewModel: AddSubscriptionViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    
    // Date picker state
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = uiState.renewalDate
    )
    
    // Show snackbar for errors
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Add Subscription") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary
                )
            )
        },
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                )
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Service Name
                item {
                    OutlinedTextField(
                        value = uiState.serviceName,
                        onValueChange = viewModel::updateServiceName,
                        label = { Text("Service Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = TextSecondary,
                            unfocusedLabelColor = TextTertiary
                        )
                    )
                }
                
                // Price
                item {
                    OutlinedTextField(
                        value = uiState.price,
                        onValueChange = viewModel::updatePrice,
                        label = { Text("Price (৳)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = TextSecondary,
                            unfocusedLabelColor = TextTertiary
                        )
                    )
                }
                
                // Billing Cycle
                item {
                    Column {
                        Text(
                            text = "Billing Cycle",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            BillingCycle.entries.forEach { cycle ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(CornerRadiusSmall))
                                        .background(
                                            if (uiState.billingCycle == cycle)
                                                Primary
                                            else
                                                GlassBackground
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (uiState.billingCycle == cycle)
                                                Primary
                                            else
                                                GlassBorder,
                                            shape = RoundedCornerShape(CornerRadiusSmall)
                                        )
                                        .clickable { viewModel.updateBillingCycle(cycle) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cycle.displayName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (uiState.billingCycle == cycle)
                                            androidx.compose.ui.graphics.Color.White
                                        else
                                            TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Renewal Date
                item {
                    Column {
                        Text(
                            text = "Next Renewal Date",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(CornerRadiusSmall))
                                .background(GlassBackground)
                                .border(
                                    width = 1.dp,
                                    color = GlassBorder,
                                    shape = RoundedCornerShape(CornerRadiusSmall)
                                )
                                .clickable { showDatePicker = true }
                                .padding(16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = TextTertiary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = dateFormat.format(Date(uiState.renewalDate)),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        Text(
                            text = "Monthly: notify 3 days before. Yearly: notify 7 days before.",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                
                // Auto Renew Toggle
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Auto Renew",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Switch(
                            checked = uiState.autoRenew,
                            onCheckedChange = viewModel::updateAutoRenew,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = androidx.compose.ui.graphics.Color.White,
                                checkedTrackColor = Primary,
                                uncheckedThumbColor = androidx.compose.ui.graphics.Color.White,
                                uncheckedTrackColor = GlassBackground
                            )
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
            
            // Save Button
            Button(
                onClick = {
                    viewModel.saveSubscription(onSuccess = onNavigateBack)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                enabled = !uiState.isSaving
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = androidx.compose.ui.graphics.Color.White
                    )
                } else {
                    Text(
                        text = "Save Subscription",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        
        // Modern Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { selectedDate ->
                                viewModel.updateRenewalDate(selectedDate)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK", color = Primary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel", color = TextTertiary)
                    }
                },
                colors = DatePickerDefaults.colors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFF2A2A2A)
                )
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = androidx.compose.ui.graphics.Color(0xFF2A2A2A),
                        selectedDayContainerColor = Primary,
                        todayContentColor = Primary,
                        todayDateBorderColor = Primary
                    )
                )
            }
        }
    }
}

