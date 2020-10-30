package com.equipo1.pgraph.providers

import com.equipo1.pgraph.models.Register
import com.equipo1.pgraph.models.RegisterSerializable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class RegistersProvider {
    private val collection = FirebaseFirestore.getInstance().collection("Registers")

    fun insert(register: Register): Task<DocumentReference> {
        return collection.add(register)
    }

    fun getRegistersByUser(id: String): Query {
        return collection.whereEqualTo("uid", id)
    }

    fun getHistory(id: String, callback: Callback<List<RegisterSerializable>>) {
        getRegistersByUser(id).orderBy("datetime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                for (document in result) {
                    val list = result.toObjects(RegisterSerializable::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

}