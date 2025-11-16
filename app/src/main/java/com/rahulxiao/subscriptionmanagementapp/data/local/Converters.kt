package com.rahulxiao.subscriptionmanagementapp.data.local

import androidx.room.TypeConverter
import com.rahulxiao.subscriptionmanagementapp.data.model.BillingCycle

class Converters {
    @TypeConverter
    fun fromBillingCycle(value: BillingCycle): String {
        return value.name
    }
    
    @TypeConverter
    fun toBillingCycle(value: String): BillingCycle {
        return BillingCycle.valueOf(value)
    }
}

