package com.rahulxiao.subscriptionmanagementapp.data.repository

import com.rahulxiao.subscriptionmanagementapp.data.local.SubscriptionDao
import com.rahulxiao.subscriptionmanagementapp.data.model.BillingCycle
import com.rahulxiao.subscriptionmanagementapp.data.model.Subscription
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {
    
    val allSubscriptions: Flow<List<Subscription>> = subscriptionDao.getAllSubscriptions()
    val monthlyTotal: Flow<Double?> = subscriptionDao.getMonthlyTotal()
    val yearlyTotal: Flow<Double?> = subscriptionDao.getYearlyTotal()
    
    suspend fun getSubscriptionById(id: Long): Subscription? {
        return subscriptionDao.getSubscriptionById(id)
    }
    
    suspend fun insertSubscription(subscription: Subscription): Long {
        return subscriptionDao.insertSubscription(subscription)
    }
    
    suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.updateSubscription(subscription)
    }
    
    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.deleteSubscription(subscription)
    }
    
    suspend fun seedMockData() {
        // Check if data already exists
        val existing = subscriptionDao.getAllSubscriptions()
        
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        
        val mockSubscriptions = listOf(
            Subscription(
                serviceName = "Netflix Premium",
                serviceInitial = "N",
                price = 999.0,
                billingCycle = BillingCycle.MONTHLY,
                renewalDate = now + (3 * 24 * 60 * 60 * 1000L), // 3 days from now
                lastUsed = now - (7 * 24 * 60 * 60 * 1000L), // 1 week ago
                category = "Entertainment",
                description = "Streaming service"
            ),
            Subscription(
                serviceName = "Spotify Premium",
                serviceInitial = "S",
                price = 149.0,
                billingCycle = BillingCycle.MONTHLY,
                renewalDate = now + (10 * 24 * 60 * 60 * 1000L), // 10 days from now
                lastUsed = now - (1 * 24 * 60 * 60 * 1000L), // 1 day ago
                category = "Music",
                description = "Music streaming"
            ),
            Subscription(
                serviceName = "Adobe Creative Cloud",
                serviceInitial = "A",
                price = 2650.0,
                billingCycle = BillingCycle.MONTHLY,
                renewalDate = now + (1 * 24 * 60 * 60 * 1000L), // 1 day from now
                lastUsed = now, // today
                category = "Productivity",
                description = "Design tools"
            ),
            Subscription(
                serviceName = "Amazon Prime",
                serviceInitial = "A",
                price = 1499.0,
                billingCycle = BillingCycle.YEARLY,
                renewalDate = now + (30 * 24 * 60 * 60 * 1000L), // 30 days from now
                lastUsed = now - (2 * 24 * 60 * 60 * 1000L), // 2 days ago
                category = "Shopping",
                description = "Prime membership"
            ),
            Subscription(
                serviceName = "YouTube Premium",
                serviceInitial = "Y",
                price = 129.0,
                billingCycle = BillingCycle.MONTHLY,
                renewalDate = now + (15 * 24 * 60 * 60 * 1000L), // 15 days from now
                lastUsed = now, // today
                category = "Entertainment",
                description = "Ad-free videos"
            ),
            Subscription(
                serviceName = "iCloud Storage",
                serviceInitial = "i",
                price = 75.0,
                billingCycle = BillingCycle.MONTHLY,
                renewalDate = now + (20 * 24 * 60 * 60 * 1000L), // 20 days from now
                lastUsed = now - (3 * 24 * 60 * 60 * 1000L), // 3 days ago
                category = "Cloud",
                description = "Cloud storage"
            )
        )
        
        subscriptionDao.insertSubscriptions(mockSubscriptions)
    }
}

