package com.contactlist.currency.data.local.dao

import androidx.room.*
import com.contactlist.currency.data.local.entity.ContactEntity


@Dao
interface ContactListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertContactList(contact: List<ContactEntity>)

    @Query("SELECT * FROM contact")
    abstract suspend fun getContactList(): List<ContactEntity>

    @Delete
    abstract suspend fun deleteContact(contactEntity: ContactEntity)
}