package com.equipo1.pgraph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }


        //Go to LoginActivity
        btnGotoLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }

        //Go to SignupActivity
        btnGotoSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }



    }
}