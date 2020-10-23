package com.equipo1.pgraph.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.equipo1.pgraph.fragments.ChartFragment
import com.equipo1.pgraph.R
import com.equipo1.pgraph.fragments.RegisterFragment
import com.equipo1.pgraph.providers.AuthProvider
import com.equipo1.pgraph.providers.UsersProvider
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val usersProvider = UsersProvider()
    private val authProvider = AuthProvider()
    val id: String?

    init {
        id = authProvider.getUid()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        getUser()

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

    private fun getUser() {
        authProvider.getUid()?.let {id ->
            usersProvider.getUser(id)?.addOnSuccessListener {documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val navHeader = nav_view.getHeaderView(0)
                    navHeader.findViewById<TextView>(R.id.username).text = documentSnapshot.getString("name")
                    navHeader.findViewById<TextView>(R.id.email).text = documentSnapshot.getString("email")
                }
            }
        }
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
            R.id.nav_chart -> {
                fragment = ChartFragment()
            }
            R.id.nav_logout -> {
                authProvider.signOut()
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