package com.equipo1.pgraph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ValidateEmail {
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            if(isFormValid()) {
                login()
            }
        }
    }


    private fun isFormValid(): Boolean {
        var isCorrect = true

        email = etEmailLogin.text.toString().trim()
        password = etPasswordLogin.text.toString()

        when {
            email.isEmpty() -> {
                tilEmailLogin.error = getString(R.string.requiered_field)
                tilEmailLogin.isErrorEnabled = true
                isCorrect = false
            }
            !isEmailValid(email) -> {
                tilEmailLogin.error = getString(R.string.invalid_email)
                tilEmailLogin.isErrorEnabled = true
                isCorrect = false
            }
            else -> {
                tilEmailLogin.isErrorEnabled = false
            }
        }

        when {
            password.isEmpty() -> {
                tilPasswordLogin.error = getString(R.string.requiered_field)
                tilPasswordLogin.isErrorEnabled = true
                isCorrect = false
            }
            else -> {
                tilPasswordLogin.isErrorEnabled = false
            }
        }

        return isCorrect
    }

    private fun login() {
        val auth = FirebaseAuth.getInstance()
        rlLoadingLogin.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {login ->
            if(login.isSuccessful) {
                rlLoadingLogin.visibility = View.INVISIBLE
                startActivity(
                    Intent(this, HomeActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
            } else {
                rlLoadingLogin.visibility = View.INVISIBLE
                dialogOnError("Credenciales no validas")
            }
        }
    }

    private fun dialogOnError(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(message)
            .setNegativeButton("Okey") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setBackground(resources.getDrawable(R.drawable.alert_dialog_bg, null))
            .setCancelable(false)
            .show()
    }

}