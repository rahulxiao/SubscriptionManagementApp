package com.rahulxiao.subscriptionmanagementapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulxiao.subscriptionmanagementapp.data.model.BillingCycle
import com.rahulxiao.subscriptionmanagementapp.data.model.Subscription
import com.rahulxiao.subscriptionmanagementapp.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddSubscriptionUiState(
    val serviceName: String = "",
    val serviceInitial: String = "",
    val price: String = "",
    val billingCycle: BillingCycle = BillingCycle.MONTHLY,
    val renewalDate: Long = System.currentTimeMillis(),
    val autoRenew: Boolean = true,
    val category: String = "Entertainment",
    val description: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)

class AddSubscriptionViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddSubscriptionUiState())
    val uiState: StateFlow<AddSubscriptionUiState> = _uiState.asStateFlow()
    
    fun updateServiceName(name: String) {
        _uiState.value = _uiState.value.copy(
            serviceName = name,
            serviceInitial = if (name.isNotEmpty()) name.first().uppercase() else ""
        )
    }
    
    fun updatePrice(price: String) {
        _uiState.value = _uiState.value.copy(price = price)
    }
    
    fun updateBillingCycle(cycle: BillingCycle) {
        _uiState.value = _uiState.value.copy(billingCycle = cycle)
    }
    
    fun updateRenewalDate(date: Long) {
        _uiState.value = _uiState.value.copy(renewalDate = date)
    }
    
    fun updateAutoRenew(autoRenew: Boolean) {
        _uiState.value = _uiState.value.copy(autoRenew = autoRenew)
    }
    
    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }
    
    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }
    
    fun saveSubscription(onSuccess: () -> Unit) {
        val state = _uiState.value
        
        // Validation
        if (state.serviceName.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Service name is required")
            return
        }
        
        val priceValue = state.price.toDoubleOrNull()
        if (priceValue == null || priceValue <= 0) {
            _uiState.value = state.copy(errorMessage = "Valid price is required")
            return
        }
        
        _uiState.value = state.copy(isSaving = true, errorMessage = null)
        
        viewModelScope.launch {
            try {
                val subscription = Subscription(
                    serviceName = state.serviceName,
                    serviceInitial = state.serviceInitial,
                    price = priceValue,
                    billingCycle = state.billingCycle,
                    renewalDate = state.renewalDate,
                    lastUsed = System.currentTimeMillis(),
                    autoRenew = state.autoRenew,
                    category = state.category,
                    description = state.description
                )
                
                repository.insertSubscription(subscription)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isSaving = false,
                    errorMessage = "Failed to save: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

