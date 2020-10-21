package com.equipo1.pgraph

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern

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
    }

}