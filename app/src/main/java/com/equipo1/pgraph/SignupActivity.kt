package com.equipo1.pgraph

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity(), ValidateEmail {
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

        name = etNameSignup.text.toString()
        email = etEmailSignup.text.toString()
        password = etPasswordSignup.text.toString()
        passwordVerification = etPasswordVerificationSignup.text.toString()

        when {
            name.isEmpty() -> {
                tilNameSignup.error = getString(R.string.requiered_field)
                tilNameSignup.isErrorEnabled = true
                isCorrect = false
            }
            name.trim().length < 6 -> {
                tilNameSignup.error = getString(R.string.min_length_6)
                tilNameSignup.isErrorEnabled = true
                isCorrect = false
            }
            else -> {
                tilNameSignup.isErrorEnabled = false
            }
        }

        when {
            email.isEmpty() -> {
                tilEmailSignup.error = getString(R.string.requiered_field)
                tilEmailSignup.isErrorEnabled = true
                isCorrect = false
            }
            !isEmailValid(email) -> {
                tilEmailSignup.error = getString(R.string.invalid_email)
                tilEmailSignup.isErrorEnabled = true
                isCorrect = false
            }
            else -> {
                tilEmailSignup.isErrorEnabled = false
            }
        }

        when {
            password.isEmpty() -> {
                tilPasswordSignup.error = getString(R.string.requiered_field)
                tilPasswordSignup.isErrorEnabled = true
                isCorrect = false
            }
            password.length < 6 -> {
                tilPasswordSignup.error = getString(R.string.min_length_6)
                tilPasswordSignup.isErrorEnabled = true
                isCorrect = false
            }
            else -> {
                tilPasswordSignup.isErrorEnabled = false
            }
        }

        when {
            passwordVerification.isEmpty() -> {
                tilPasswordVerificationSignup.error = getString(R.string.requiered_field)
                tilPasswordVerificationSignup.isErrorEnabled = true
                isCorrect = false
            }
            passwordVerification != password -> {
                tilPasswordVerificationSignup.error = "Las contraseñas no coinciden"
                tilPasswordVerificationSignup.isErrorEnabled = true
                isCorrect = false
            }
            else -> {
                tilPasswordVerificationSignup.isErrorEnabled = false
            }
        }


        return isCorrect
    }

    private fun signUp() {
        val auth = FirebaseAuth.getInstance()
        rlLoadingSignup.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { register ->
            when {
                register.isSuccessful -> {
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
                }
                else -> {
                    rlLoadingSignup.visibility = View.INVISIBLE
                    Toast.makeText(this, "Hubo un error en la operación", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}