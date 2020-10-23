package com.equipo1.pgraph.providers

import com.equipo1.pgraph.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UsersProvider {
    private val collection: CollectionReference = FirebaseFirestore.getInstance().collection("Users")

    fun getUser(id: String): Task<DocumentSnapshot>? {
        return collection.document(id).get()
    }

    fun insert(id: String, user: User): Task<Void>? {
        return collection.document(id).set(user)
    }

    fun update(id: String, user: User): Task<Void>? {

        val map = mapOf(
            "name" to user.name
        )

        return collection.document(id).update(map)
    }

}