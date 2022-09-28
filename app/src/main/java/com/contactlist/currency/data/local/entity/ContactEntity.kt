package com.contactlist.currency.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val customerId: String,
    val companyName: String,
    val contactName: String,
    val title: String,
    val address: String,
    val city: String,
    val email: String,
    val postalCode: String,
    val country: String,
    val phone: String,
    val fax: String
)
