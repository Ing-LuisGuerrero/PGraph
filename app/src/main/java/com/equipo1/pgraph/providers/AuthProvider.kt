package com.equipo1.pgraph.providers

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProvider {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getEmail(): String? {
        return if(auth.currentUser != null) {
            auth.currentUser!!.email
        } else {
            null
        }
    }

    fun signup(email: String, password: String): Task<AuthResult>{
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun getUid(): String? {
        return if(auth.currentUser != null) {
            auth.currentUser!!.uid
        } else {
            null
        }
    }

    fun signOut() = auth.signOut()

}