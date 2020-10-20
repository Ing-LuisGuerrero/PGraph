package com.equipo1.pgraph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Go to LoginActivity
        btnGotoLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }

        //Go to SignupActivity
        btnGotoSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }



    }
}