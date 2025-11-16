package com.rahulxiao.subscriptionmanagementapp.data.local

import androidx.room.*
import com.rahulxiao.subscriptionmanagementapp.data.model.Subscription
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    
    @Query("SELECT * FROM subscriptions ORDER BY renewalDate ASC")
    fun getAllSubscriptions(): Flow<List<Subscription>>
    
    @Query("SELECT * FROM subscriptions WHERE id = :subscriptionId")
    suspend fun getSubscriptionById(subscriptionId: Long): Subscription?
    
    @Query("SELECT * FROM subscriptions WHERE renewalDate >= :startDate AND renewalDate <= :endDate")
    fun getSubscriptionsByDateRange(startDate: Long, endDate: Long): Flow<List<Subscription>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscription): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriptions(subscriptions: List<Subscription>)
    
    @Update
    suspend fun updateSubscription(subscription: Subscription)
    
    @Delete
    suspend fun deleteSubscription(subscription: Subscription)
    
    @Query("DELETE FROM subscriptions")
    suspend fun deleteAllSubscriptions()
    
    @Query("SELECT SUM(price) FROM subscriptions WHERE billingCycle = 'MONTHLY'")
    fun getMonthlyTotal(): Flow<Double?>
    
    @Query("SELECT SUM(price * 12) FROM subscriptions WHERE billingCycle = 'YEARLY'")
    fun getYearlyTotal(): Flow<Double?>
}

