package com.kiriuru.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kiriuru.weatherapp.R
import com.kiriuru.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
   // private lateinit var navController: NavController
    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)
                as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navView: BottomNavigationView = mBinding.navView
        val toolbar= mBinding.toolbar
        mBinding.navView.isVisible = true

//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment_activity_main)
//                as NavHostFragment
//        navController = navHostFragment.navController
        setSupportActionBar(toolbar)

        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.daily_weather, R.id.tomorrow_weather, R.id.weekly_weather
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)
        navView.setupWithNavController(navController)
    }
}