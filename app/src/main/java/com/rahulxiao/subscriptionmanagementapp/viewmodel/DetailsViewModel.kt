package com.rahulxiao.subscriptionmanagementapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulxiao.subscriptionmanagementapp.data.model.Subscription
import com.rahulxiao.subscriptionmanagementapp.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailsUiState(
    val subscription: Subscription? = null,
    val isLoading: Boolean = true,
    val priceHistory: List<PricePoint> = emptyList()
)

data class PricePoint(
    val date: Long,
    val price: Double
)

class DetailsViewModel(
    private val repository: SubscriptionRepository,
    private val subscriptionId: Long
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()
    
    init {
        loadSubscription()
    }
    
    private fun loadSubscription() {
        viewModelScope.launch {
            val subscription = repository.getSubscriptionById(subscriptionId)
            
            // Mock price history data
            val history = listOf(
                PricePoint(System.currentTimeMillis() - 90L * 24 * 60 * 60 * 1000, subscription?.price?.minus(50) ?: 0.0),
                PricePoint(System.currentTimeMillis() - 60L * 24 * 60 * 60 * 1000, subscription?.price ?: 0.0),
                PricePoint(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000, subscription?.price ?: 0.0),
                PricePoint(System.currentTimeMillis(), subscription?.price ?: 0.0)
            )
            
            _uiState.value = DetailsUiState(
                subscription = subscription,
                isLoading = false,
                priceHistory = history
            )
        }
    }
    
    fun deleteSubscription(onDeleted: () -> Unit) {
        viewModelScope.launch {
            _uiState.value.subscription?.let {
                repository.deleteSubscription(it)
                onDeleted()
            }
        }
    }
    
    fun toggleAutoRenew() {
        viewModelScope.launch {
            _uiState.value.subscription?.let { sub ->
                val updated = sub.copy(autoRenew = !sub.autoRenew)
                repository.updateSubscription(updated)
                _uiState.value = _uiState.value.copy(subscription = updated)
            }
        }
    }
}

