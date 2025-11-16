package com.rahulxiao.subscriptionmanagementapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulxiao.subscriptionmanagementapp.data.model.Subscription
import com.rahulxiao.subscriptionmanagementapp.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AnalyticsUiState(
    val monthlyTotal: Double = 0.0,
    val yearlyTotal: Double = 0.0,
    val biggestExpense: Subscription? = null,
    val categoryBreakdown: Map<String, Double> = emptyMap(),
    val monthlyTrend: List<TrendPoint> = emptyList(),
    val upcomingRenewals: List<Subscription> = emptyList(),
    val isLoading: Boolean = true
)

data class TrendPoint(
    val month: String,
    val amount: Double
)

class AnalyticsViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()
    
    private val _timeframe = MutableStateFlow(Timeframe.ONE_MONTH)
    val timeframe: StateFlow<Timeframe> = _timeframe.asStateFlow()
    
    init {
        loadAnalytics()
    }
    
    private fun loadAnalytics() {
        viewModelScope.launch {
            combine(
                repository.allSubscriptions,
                repository.monthlyTotal,
                repository.yearlyTotal
            ) { subscriptions, monthly, yearly ->
                // Calculate category breakdown
                val categoryMap = subscriptions.groupBy { it.category }
                    .mapValues { entry ->
                        entry.value.sumOf { it.price }
                    }
                
                // Find biggest expense
                val biggest = subscriptions.maxByOrNull { it.price }
                
                // Mock monthly trend (last 6 months)
                val trend = listOf(
                    TrendPoint("Oct", 2500.0),
                    TrendPoint("Nov", 2800.0),
                    TrendPoint("Dec", 3200.0),
                    TrendPoint("Jan", 3000.0),
                    TrendPoint("Feb", 3100.0),
                    TrendPoint("Mar", monthly ?: 0.0)
                )
                
                // Upcoming renewals (next 30 days)
                val thirtyDaysFromNow = System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)
                val upcoming = subscriptions.filter { 
                    it.renewalDate <= thirtyDaysFromNow 
                }.sortedBy { it.renewalDate }
                
                AnalyticsUiState(
                    monthlyTotal = monthly ?: 0.0,
                    yearlyTotal = yearly ?: 0.0,
                    biggestExpense = biggest,
                    categoryBreakdown = categoryMap,
                    monthlyTrend = trend,
                    upcomingRenewals = upcoming,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
    
    fun setTimeframe(timeframe: Timeframe) {
        _timeframe.value = timeframe
    }
}

enum class Timeframe(val displayName: String) {
    ONE_MONTH("1M"),
    THREE_MONTHS("3M"),
    ONE_YEAR("1Y")
}

