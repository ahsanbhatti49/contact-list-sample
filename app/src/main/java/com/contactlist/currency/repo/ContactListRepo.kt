package com.contactlist.currency.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.contactlist.currency.app.AppExecutors
import com.contactlist.currency.data.api.ApiServices
import com.contactlist.currency.data.api.network.NetworkAndDBBoundResource
import com.contactlist.currency.data.api.network.Resource
import com.contactlist.currency.data.local.dao.ContactListDao
import com.contactlist.currency.data.model.ContactModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContactListRepo @Inject constructor(
    private val contactListDao: ContactListDao,
    private val apiServices: ApiServices,
    @ApplicationContext val context: Context,
    private val appExecutors: AppExecutors = AppExecutors()
) {
    fun getContactList(firstCall: Boolean): LiveData<Resource<ContactModel?>> {
        val data = HashMap<String, String>()
        data["app_id"] = "BuildConfig.API_KEY"

        return object :
            NetworkAndDBBoundResource<ContactModel, ContactModel>(appExecutors) {
            override fun saveCallResult(item: ContactModel) {
                contactListDao.insertCurrencyRates(item)
            }

            override fun shouldFetch(data: ContactModel?): Boolean {
                return  (firstCall || checkIfFirstCall())
            }

            override fun loadFromDb(): LiveData<ContactModel> {
                return contactListDao.getLocalCurrencyData()
            }

            override fun createCall(): LiveData<Resource<ContactModel>> =
                apiServices.getNewsSource(data)

        }.asLiveData()

    }

    fun checkIfFirstCall(): Boolean {
        return contactListDao.getLocalCurrencyData().value == null
    }

}