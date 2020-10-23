package com.equipo1.pgraph.providers

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProvider {
    lateinit var auth: FirebaseAuth

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getEmail(): String? {
        if(auth.currentUser != null) {
            return auth.currentUser!!.email
        } else {
            return null
        }
    }

    fun signup(email: String, password: String): Task<AuthResult>{
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun getUid(): String? {
        if(auth.currentUser != null) {
            return auth.currentUser!!.uid
        } else {
            return null
        }
    }
}