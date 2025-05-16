package com.example.vizzio.View

import Home
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.vizzio.R
import com.google.android.material.navigation.NavigationView

class fragmentswitcher : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragmentswitcher)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.mytoolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        if(savedInstanceState == null){
            replaceFragment(Home())
            navigationView.setCheckedItem(R.id.nav_Home)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_Home -> replaceFragment(Home())
            R.id.nav_prof -> replaceFragment(Profile())
            R.id.nav_setting -> replaceFragment(Settings())
            R.id.nav_share -> replaceFragment(support())
            R.id.nav_logout -> {startActivity(Intent(this@fragmentswitcher,Login::class.java))
            Toast.makeText(this@fragmentswitcher,"Logged Out Succesfully",Toast.LENGTH_SHORT).show()}}
        drawerLayout.closeDrawer((GravityCompat.START))
        return true
    }
    private fun replaceFragment(fragment: Fragment){
        val transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentcontainer,fragment)
        transaction.commit()
    }

    override fun onBackPressed(){
        super.onBackPressed()
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else{
                onBackPressedDispatcher.onBackPressed()

            }
    }


}