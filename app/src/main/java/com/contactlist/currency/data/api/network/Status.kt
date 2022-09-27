package com.contactlist.currency.data.api.network


sealed class Status {
    object LOADING : Status()
    object SUCCESS : Status()
    object ERROR : Status()
    object IDLE : Status()

    fun isSuccessful() = this == SUCCESS
    fun isLoading() = this == LOADING
    fun isError() = this == ERROR
    fun isIdle() = this == IDLE
}
