package com.contactlist.currency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.contactlist.currency.data.local.dao.ContactListDao
import com.contactlist.currency.data.local.entity.ContactEntity
import com.contactlist.currency.data.model.ContactModel

@Database(entities = [ContactEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactListDao(): ContactListDao

}