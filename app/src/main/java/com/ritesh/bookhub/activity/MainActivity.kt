package com.ritesh.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.ritesh.bookhub.fragment.ProfileFragment
import com.ritesh.bookhub.R
import com.ritesh.bookhub.fragment.AboutAppFragment
import com.ritesh.bookhub.fragment.DashboardFragment
import com.ritesh.bookhub.fragment.FavouritesFragment

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout : androidx.drawerlayout.widget.DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frame :FrameLayout
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var navigationView : NavigationView
    var previousMenuItem : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frame = findViewById(R.id.frame)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)


        setUPToolbar()
        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null)
            {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId)
            {
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }


                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,  FavouritesFragment())
                        .commit()
                    supportActionBar?.title ="Favorites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,  ProfileFragment())
                        .commit()
                    supportActionBar?.title ="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp-> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,  AboutAppFragment())
                        .commit()
                    supportActionBar?.title ="AboutApp"
                    drawerLayout.closeDrawers()
                }




            }




            return@setNavigationItemSelectedListener true
        }


    }

    fun setUPToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId


            if(id == android.R.id.home){
                drawerLayout.openDrawer(GravityCompat.START)
            }
            return super.onOptionsItemSelected(item)
        }

    fun openDashboard(){

        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()

        supportActionBar?.title ="Dashboard"

        navigationView.setCheckedItem(R.id.dashboard)


    }

    override fun onBackPressed()
    {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag)
        { !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }








    }
