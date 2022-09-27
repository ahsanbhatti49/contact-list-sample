package com.contactlist.currency.data.api

import androidx.lifecycle.LiveData
import com.contactlist.currency.data.api.network.Resource
import com.contactlist.currency.data.model.ContactModel
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ApiServices {
    @GET("latest.json")
    fun getNewsSource(@QueryMap options: Map<String, String>): LiveData<Resource<ContactModel>>
}
