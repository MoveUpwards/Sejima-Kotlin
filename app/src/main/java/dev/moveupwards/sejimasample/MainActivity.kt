package dev.moveupwards.sejimasample

import android.os.Bundle
import android.view.Menu

import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                supportActionBar?.title = "HOME"
                navigateToFragment(Fragment())
            }
            R.id.nav_mu_button -> {
                navigateToFragment(FragmentMUButton())
/*
                // Start new activity
                val intent = Intent(this, MainActivity::class.java) // WARNING DO NOT USE FRAGMENT
                startActivity(intent)
*/
            }
            R.id.nav_mu_header -> {
                navigateToFragment(FragmentMUHeader())
            }
            R.id.nav_mu_card -> {
                navigateToFragment(FragmentMUCard())
            }
            R.id.nav_mu_topbar -> {
                navigateToFragment(FragmentMUTopBar())
            }
            R.id.nav_mu_pincode -> {
                navigateToFragment(FragmentMUPinCode())
            }
            R.id.nav_mu_textfield -> {
                navigateToFragment(FragmentMUTextField())
            }
            R.id.nav_mu_navigationbar -> {
                navigateToFragment(FragmentMUNavigationBar())
            }
            R.id.nav_mu_avatar -> {
                navigateToFragment(FragmentMUAvatar())
            }
            R.id.nav_mu_horizontalpager -> {
                navigateToFragment(FragmentMUHorizontalPager())
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
