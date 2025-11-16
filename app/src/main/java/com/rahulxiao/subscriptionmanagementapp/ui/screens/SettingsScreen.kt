package com.rahulxiao.subscriptionmanagementapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.ui.components.AppBottomNavigation
import com.rahulxiao.subscriptionmanagementapp.ui.theme.*

@Composable
fun SettingsScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToNotes: () -> Unit = {},
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var priceAlertsEnabled by remember { mutableStateOf(true) }
    var showThemeDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // Profile Header
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "U",
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "User Name",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        text = "user@example.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiary
                    )
                }
            }
            
            // Account Section
            item {
                SettingsSection(title = "Account") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Edit Profile",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.Lock,
                        title = "Change Password",
                        onClick = { }
                    )
                }
            }
            
            // Appearance Section
            item {
                SettingsSection(title = "Appearance") {
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Theme",
                        subtitle = ThemeManager.currentTheme.name,
                        onClick = { showThemeDialog = true }
                    )
                }
            }
            
            // Notifications Section
            item {
                SettingsSection(title = "Notifications") {
                    SettingsToggleItem(
                        icon = Icons.Default.Notifications,
                        title = "Push Notifications",
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                    SettingsToggleItem(
                        icon = Icons.Default.Warning,
                        title = "Price Alerts",
                        checked = priceAlertsEnabled,
                        onCheckedChange = { priceAlertsEnabled = it }
                    )
                }
            }
            
            // Data Section
            item {
                SettingsSection(title = "Data") {
                    SettingsItem(
                        icon = Icons.Default.Share,
                        title = "Export Data (CSV)",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Backup & Restore",
                        onClick = { }
                    )
                }
            }
            
            // About Section
            item {
                SettingsSection(title = "About") {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "App Version",
                        subtitle = "1.0.0",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "Terms & Privacy",
                        onClick = { }
                    )
                }
            }
            
            // Danger Zone
            item {
                SettingsSection(title = "Danger Zone") {
                    SettingsItem(
                        icon = Icons.Default.Delete,
                        title = "Delete Account",
                        titleColor = AccentPink,
                        onClick = { }
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        
        // Bottom Navigation
        AppBottomNavigation(
            currentRoute = currentRoute,
            onNavigate = { route ->
                when (route) {
                    "dashboard" -> onNavigateToDashboard()
                    "analytics" -> onNavigateToAnalytics()
                    "notes" -> onNavigateToNotes()
                }
            },
            onAddClick = onNavigateToAdd,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    
    // Theme Selector Dialog
    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = ThemeManager.currentTheme,
            onDismiss = { showThemeDialog = false },
            onThemeSelected = { theme ->
                ThemeManager.setTheme(theme)
                showThemeDialog = false
            }
        )
    }
}

@Composable
private fun SettingsSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
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
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    titleColor: Color = TextPrimary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = titleColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = titleColor
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiary
                )
            }
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = TextTertiary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = GlassBackground
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeSelectionDialog(
    currentTheme: ThemeColors,
    onDismiss: () -> Unit,
    onThemeSelected: (ThemeColors) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2A2A2A),
        title = {
            Text(
                text = "Select Theme",
                style = MaterialTheme.typography.displayMedium,
                color = TextPrimary
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AppThemes.getAllThemes().forEach { theme ->
                    ThemeOption(
                        theme = theme,
                        isSelected = theme.name == currentTheme.name,
                        onClick = { onThemeSelected(theme) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextTertiary)
            }
        }
    )
}

@Composable
private fun ThemeOption(
    theme: ThemeColors,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerRadiusSmall))
            .background(if (isSelected) GlassBackground else Color.Transparent)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) theme.primary else GlassBorder,
                shape = RoundedCornerShape(CornerRadiusSmall)
            )
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color preview circles
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(theme.primary)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(theme.secondary)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(theme.accentPeach)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = theme.name,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) TextPrimary else TextSecondary,
            modifier = Modifier.weight(1f)
        )
        
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = theme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

