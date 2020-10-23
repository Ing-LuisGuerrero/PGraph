package com.equipo1.pgraph.providers

import com.equipo1.pgraph.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UsersProvider {
    lateinit var collection: CollectionReference

    init {
        collection = FirebaseFirestore.getInstance().collection("Users")
    }

    fun getUser(id: String): Task<DocumentSnapshot>? {
        return collection.document(id).get()
    }

    fun insert(user: User): Task<Void>? {
        return user.id?.let { collection.document(it).set(user) }
    }

    fun update(user: User): Task<Void>? {

        val map = mapOf<String, String>(
            "name" to user.name
        )

        return user.id?.let { collection.document(it).update(map) }
    }

}