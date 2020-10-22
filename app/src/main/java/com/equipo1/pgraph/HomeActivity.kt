package com.equipo1.pgraph

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        nav_view.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setCheckedItem(R.id.nav_register)
        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment: Fragment = RegisterFragment()

        when (item.itemId) {
            R.id.nav_register -> {
                fragment = RegisterFragment()

            }
            R.id.nav_chart -> {
                fragment = ChartFragment()
            }
            R.id.nav_logout -> {
                val auth = FirebaseAuth.getInstance()
                auth.signOut()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        toolbar.title = item.title
        transaction.replace(R.id.fragmentContent, fragment)
        transaction.commit()

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }
}