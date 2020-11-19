package com.equipo1.pgraph.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception
import com.equipo1.pgraph.models.Register
import com.equipo1.pgraph.models.RegisterSerializable
import com.equipo1.pgraph.providers.AuthProvider
import com.equipo1.pgraph.providers.Callback
import com.equipo1.pgraph.providers.RegistersProvider

class HistoryViewModel(): ViewModel() {

    private val registersProvider = RegistersProvider()
    val listHistory: MutableLiveData<List<RegisterSerializable>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    private val authProvider = AuthProvider()
    val id: String

    fun refresh() {
        getHistoryFromFirebase()
    }

    init {
        id = authProvider.getUid().toString()
    }

    private fun getHistoryFromFirebase() {
        registersProvider.getHistory(id, object: Callback<List<RegisterSerializable>> {
            override fun onSuccess(result: List<RegisterSerializable>?) {
                listHistory.postValue(result)
                Log.d("Cayo", "Dentro de onSuccess")
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                Log.d("Cayo", "Dentro de onFailed")
                processFinished()
            }

        })
    }


    fun processFinished() {
        isLoading.value = false
    }

}