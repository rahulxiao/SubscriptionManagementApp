package com.rahulxiao.subscriptionmanagementapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeManager {
    var currentTheme by mutableStateOf(AppThemes.DEFAULT)
        private set
    
    fun setTheme(theme: ThemeColors) {
        currentTheme = theme
    }
    
    fun setThemeByName(name: String) {
        currentTheme = AppThemes.getThemeByName(name)
    }
}

