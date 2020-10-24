package com.equipo1.pgraph.providers

import com.equipo1.pgraph.models.Register
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

}