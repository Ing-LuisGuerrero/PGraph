package com.equipo1.pgraph.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.equipo1.pgraph.R
import com.equipo1.pgraph.ValidateEmail
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity(), ValidateEmail {
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        btnCancelResetPassword.setOnClickListener { finish() }
        btnSendEmailToResetPassword.setOnClickListener {
            if(isFormValid()) {
                sendEmailToResetPassword()
            }
        }
    }

    private fun isFormValid(): Boolean {
        email = etEmailResetPass.text.toString().trim()

        when {
            email.isEmpty() -> {
                tilEmailResetPass.error = getString(R.string.requiered_field)
                tilEmailResetPass.isErrorEnabled = true
                return false
            }
            !isEmailValid(email) -> {
                tilEmailResetPass.error = getString(R.string.invalid_email)
                tilEmailResetPass.isErrorEnabled = true
                return false
            }
            else -> {
                tilEmailResetPass.isErrorEnabled = false
                return true
            }
        }
    }

    private fun sendEmailToResetPassword() {
        val auth = FirebaseAuth.getInstance()

        rlLoadingResetPassword.visibility = View.VISIBLE
        auth.sendPasswordResetEmail(email).addOnCompleteListener {task ->

            rlLoadingResetPassword.visibility = View.INVISIBLE

            if(task.isSuccessful) {
                dialogOnSuccessful()
            } else {
                Toast.makeText(this, "Hubo un error en la operación", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun dialogOnSuccessful() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirmacion")
            .setMessage("El email ha sido enviado")
            .setPositiveButton("Okey") { dialogInterface, _ ->
                dialogInterface.dismiss()
                finish()
            }
            .setBackground(ResourcesCompat.getDrawable(resources, R.drawable.alert_dialog_bg, null))
            .setCancelable(false)
            .show()
    }


}