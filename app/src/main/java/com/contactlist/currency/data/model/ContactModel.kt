package com.contactlist.currency.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class ContactModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val base: String,
    val disclaimer: String,
    val license: String,
)
