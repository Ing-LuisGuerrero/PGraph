package com.equipo1.pgraph

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap
import kotlin.math.log

class SignupActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var passwordVerification: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ivArrowBack.setOnClickListener { finish() }

        btnSignup.setOnClickListener {
            if(isFormValid()) {
                signUp()
            }
        }
    }

    private fun isFormValid(): Boolean {

        var isCorrect = true

        name = etName.text.toString()
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        passwordVerification = etPasswordVerification.text.toString()

        if(name.isEmpty()) {
            tilName.error = getString(R.string.requiered_field)
            tilName.isErrorEnabled = true
            isCorrect = false
        } else if (name.trim().length < 6){
            tilName.error = getString(R.string.min_length_6)
            tilName.isErrorEnabled = true
            isCorrect = false
        } else {
            tilName.isErrorEnabled = false
        }

        if(email.isEmpty()) {
            tilEmail.error = getString(R.string.requiered_field)
            tilEmail.isErrorEnabled = true
            isCorrect = false
        } else if(!isEmailValid()) {
            etEmail.error = "Email no valido"
            isCorrect = false
        } else {
            tilEmail.isErrorEnabled = false
        }

        if(password.isEmpty()) {
            tilPassword.error = getString(R.string.requiered_field)
            tilPassword.isErrorEnabled = true
            isCorrect = false
        } else if(password.length < 6) {
            tilPassword.error = getString(R.string.min_length_6)
            tilPassword.isErrorEnabled = true
            isCorrect = false
        } else {
            tilPassword.isErrorEnabled = false
        }

        if(passwordVerification.isEmpty()) {
            tilPasswordVerification.error = getString(R.string.requiered_field)
            tilPasswordVerification.isErrorEnabled = true
            isCorrect = false
        } else if(passwordVerification != password) {
            tilPasswordVerification.error = "Las contraseñas no coinciden"
            tilPasswordVerification.isErrorEnabled = true
            isCorrect = false
        } else {
            tilPasswordVerification.isErrorEnabled = false
        }


        return isCorrect
    }

    private fun isEmailValid(): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }

    private fun signUp() {
        Toast.makeText(this, "Correcto", Toast.LENGTH_LONG).show()
        val auth = FirebaseAuth.getInstance()
        rlLoadingSignup.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { register ->
            if(register.isSuccessful) {
                val uID = auth.currentUser?.uid
                val data = hashMapOf(
                    "name" to name,
                    "email" to email
                )

                val db = FirebaseFirestore.getInstance()

                if (uID != null) {
                    db.collection("Users").document(uID).set(data).addOnCompleteListener {storeData ->
                        if(storeData.isSuccessful) {
                            rlLoadingSignup.visibility = View.INVISIBLE
                            startActivity(
                                Intent(this, HomeActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            )
                            finish()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Algo salió mal, intentelo mas tarde", Toast.LENGTH_LONG).show()
            }
        }
    }

}