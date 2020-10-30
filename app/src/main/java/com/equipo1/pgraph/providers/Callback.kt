package com.equipo1.pgraph.providers

interface Callback<T> {
    fun onSuccess(result: T?)
    fun onFailed(exception: Exception)
}