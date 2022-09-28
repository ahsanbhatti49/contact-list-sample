package com.contactlist.currency.repo

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.util.Log
import com.contactlist.currency.data.local.dao.ContactListDao
import com.contactlist.currency.data.local.entity.ContactEntity
import com.contactlist.currency.utils.XmlParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

class ContactListRepo @Inject constructor(
    private val contactListDao: ContactListDao,
    @ApplicationContext val context: Context,
    private val assets: AssetManager,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun getContactList(): List<ContactEntity> {
        val firstTime = sharedPreferences.getBoolean("firstTime", true)
        if (firstTime)
            fetchDataFromParser()
        return contactListDao.getContactList()
    }

    private suspend fun fetchDataFromParser() {
        withContext(Dispatchers.IO) {
            val assetManager: AssetManager = assets
            val xmlStream: InputStream = assetManager.open("ab.xml")
            try {
                val list: List<ContactEntity> = XmlParser.parse(xmlStream)
                contactListDao.insertContactList(list.distinctBy { it.customerId })
                sharedPreferences.edit().putBoolean("firstTime", false).apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun deleteContact(contactEntity: ContactEntity) {
        withContext(Dispatchers.IO) {
            contactListDao.deleteContact(contactEntity)
        }
    }
}