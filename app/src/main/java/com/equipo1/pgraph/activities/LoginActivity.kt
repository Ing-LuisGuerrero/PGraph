package com.equipo1.pgraph.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.equipo1.pgraph.R
import com.equipo1.pgraph.ValidateEmail
import com.equipo1.pgraph.providers.AuthProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        btnGotoResetPassword.setOnClickListener { startActivity(Intent(this, ResetPasswordActivity::class.java)) }
        btnCancelLogin.setOnClickListener { finish() }
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
        val authProvider = AuthProvider()
        rlLoadingLogin.visibility = View.VISIBLE

        authProvider.login(email, password).addOnCompleteListener {login ->
            rlLoadingLogin.visibility = View.INVISIBLE
            if(login.isSuccessful) {
                startActivity(
                    Intent(this, HomeActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Credenciales no validas")
                    .setNegativeButton("Okey") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }
                    .setBackground(ResourcesCompat.getDrawable(resources,
                        R.drawable.alert_dialog_bg, null))
                    .setCancelable(false)
                    .show()
            }
        }
    }
}