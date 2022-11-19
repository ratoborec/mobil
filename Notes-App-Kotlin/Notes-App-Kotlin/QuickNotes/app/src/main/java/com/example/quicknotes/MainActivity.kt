package com.example.quicknotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView : NavigationView
    lateinit var appBarConfiguration: AppBarConfiguration

    //Step 1: making nav controller
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.setDisplayHomeAsUpEnabled(false);

        //Step 2:
        navController = findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)

        drawerLayout = findViewById(R.id.drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.mapsActivity),
            drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navView = findViewById(R.id.navView)
        NavigationUI.setupWithNavController(navView, navController)

//        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    //Step 3:
    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }

}