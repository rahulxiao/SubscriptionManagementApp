package com.rahulxiao.subscriptionmanagementapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val serviceName: String,
    val serviceInitial: String,
    val price: Double,
    val currency: String = "৳", // Bangladeshi Taka
    val billingCycle: BillingCycle,
    val renewalDate: Long, // timestamp
    val lastUsed: Long, // timestamp
    val autoRenew: Boolean = true,
    val category: String = "Entertainment",
    val description: String = "",
    val notificationDays: Int = 3 // notify before X days
)

enum class BillingCycle(val displayName: String, val months: Int) {
    MONTHLY("Monthly", 1),
    YEARLY("Yearly", 12)
}

// Helper extension functions
fun Subscription.getDaysUntilRenewal(): Int {
    val now = System.currentTimeMillis()
    val diff = renewalDate - now
    return (diff / (1000 * 60 * 60 * 24)).toInt()
}

fun Subscription.getLastUsedText(): String {
    val now = System.currentTimeMillis()
    val diff = now - lastUsed
    val days = (diff / (1000 * 60 * 60 * 24)).toInt()
    
    return when {
        days == 0 -> "Today"
        days == 1 -> "Yesterday"
        days < 7 -> "$days days ago"
        days < 30 -> "${days / 7} weeks ago"
        else -> "${days / 30} months ago"
    }
}

fun Subscription.getFormattedPrice(): String {
    return "$currency${String.format("%.0f", price)}"
}

