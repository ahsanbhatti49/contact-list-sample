package com.contactlist.currency.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.contactlist.currency.data.model.ContactModel


@Dao
interface ContactListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyRates(contact: ContactModel)

    @Query("SELECT * FROM rates")
    fun getLocalCurrencyData(): LiveData<ContactModel>


}