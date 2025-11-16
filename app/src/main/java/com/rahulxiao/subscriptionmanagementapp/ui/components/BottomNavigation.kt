package com.rahulxiao.subscriptionmanagementapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GlassBackground
import com.rahulxiao.subscriptionmanagementapp.ui.theme.GlassBorder



sealed class BottomNavItem(val icon: ImageVector, val route: String) {
    object Home : BottomNavItem(Icons.Default.Home, "dashboard")
    object Analytics : BottomNavItem(Icons.Default.Info, "analytics")
    object Add : BottomNavItem(Icons.Default.Add, "add")
    object Notes : BottomNavItem(Icons.Default.Edit, "notes")
    object Profile : BottomNavItem(Icons.Default.Person, "settings")
}

@Composable
fun AppBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(86.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background with blur
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(35.dp))
                .background(GlassBackground.copy(alpha = 0.8f))
                .border(
                    width = 1.dp,
                    color = GlassBorder,
                    shape = RoundedCornerShape(35.dp)
                )
        )
        
        // Icons on top (no blur)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavIconButton(
                icon = BottomNavItem.Home.icon,
                selected = currentRoute == BottomNavItem.Home.route,
                onClick = { onNavigate(BottomNavItem.Home.route) }
            )
            
            NavIconButton(
                icon = BottomNavItem.Analytics.icon,
                selected = currentRoute == BottomNavItem.Analytics.route,
                onClick = { onNavigate(BottomNavItem.Analytics.route) }
            )
            
            // Spacer for FAB
            Spacer(modifier = Modifier.width(64.dp))
            
            NavIconButton(
                icon = BottomNavItem.Notes.icon,
                selected = currentRoute == BottomNavItem.Notes.route,
                onClick = { onNavigate(BottomNavItem.Notes.route) }
            )
            
            NavIconButton(
                icon = BottomNavItem.Profile.icon,
                selected = currentRoute == BottomNavItem.Profile.route,
                onClick = { onNavigate(BottomNavItem.Profile.route) }
            )
        }
        
        // Central FAB
        GradientFAB(
            onClick = onAddClick,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun NavIconButton(
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(48.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Color(0xFF1A1A1A) 
                   else Color(0xFF1A1A1A).copy(alpha = 0.5f),
            modifier = Modifier.size(26.dp)
        )
    }
}

