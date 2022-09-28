package com.contactlist.currency.ui.vm

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contactlist.currency.data.api.network.Status
import com.contactlist.currency.data.local.entity.ContactEntity
import com.contactlist.currency.repo.ContactListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ContactListRepo,
) : ViewModel() {

    private val _contactList: MutableLiveData<List<ContactEntity>> = MutableLiveData()
    val contactList: MutableLiveData<List<ContactEntity>> = MutableLiveData()
    var _uiState: MutableLiveData<Status> = MutableLiveData()

    init {
        _uiState.value = Status.IDLE
        getContactList()
    }

    private fun getContactList(): LiveData<List<ContactEntity>> {
        _uiState.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repo.getContactList().distinctBy { it.customerId }
                _contactList.value = result
                contactList.value = _contactList.value
                _uiState.value = Status.SUCCESS
            } catch (e: Exception) {
                _uiState.value = Status.ERROR
            }
        }
        return contactList
    }

    fun deleteContact(contactEntity: ContactEntity) {
        viewModelScope.launch {
            repo.deleteContact(contactEntity)
            getContactList()
        }
    }
}

@BindingAdapter("onClick")
fun onClick(view: View, onClick: () -> Unit) {
    view.setOnClickListener {
        onClick()
    }
}
