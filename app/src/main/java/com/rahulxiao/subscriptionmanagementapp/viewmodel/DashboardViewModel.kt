package com.rahulxiao.subscriptionmanagementapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulxiao.subscriptionmanagementapp.data.model.Subscription
import com.rahulxiao.subscriptionmanagementapp.data.model.getDaysUntilRenewal
import com.rahulxiao.subscriptionmanagementapp.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardUiState(
    val subscriptions: List<Subscription> = emptyList(),
    val monthlyTotal: Double = 0.0,
    val nextRenewal: String = "",
    val isLoading: Boolean = false
)

class DashboardViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadSubscriptions()
    }
    
    private fun loadSubscriptions() {
        viewModelScope.launch {
            combine(
                repository.allSubscriptions,
                repository.monthlyTotal
            ) { subscriptions, monthlyTotal ->
                val nextSub = subscriptions.minByOrNull { it.renewalDate }
                val nextRenewalText = nextSub?.let {
                    val days = it.getDaysUntilRenewal()
                    when {
                        days == 0 -> "Next: ${it.serviceName} today"
                        days == 1 -> "Next: ${it.serviceName} tomorrow"
                        days > 0 -> "Next: ${it.serviceName} in $days days"
                        else -> "Overdue: ${it.serviceName}"
                    }
                } ?: "No subscriptions"
                
                DashboardUiState(
                    subscriptions = subscriptions,
                    monthlyTotal = monthlyTotal ?: 0.0,
                    nextRenewal = nextRenewalText,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
    
    fun deleteSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.deleteSubscription(subscription)
        }
    }
}

