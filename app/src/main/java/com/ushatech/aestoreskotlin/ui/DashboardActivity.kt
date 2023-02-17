package com.ushatech.aestoreskotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.databinding.ActivityMainBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClicks()
        // Please call methods after setContentView for Navigation UI Component
        initNav()
    }

    private fun initNav() {
        val navController: NavController =
            Navigation.findNavController(
                this@DashboardActivity,
                R.id.activity_main_nav_host_fragment
            )
        val bottomNavigationView = binding.bottomNavigationbar


        bottomNavigationView.getOrCreateBadge(R.id.cartFragment).number = 2

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun initClicks() {


    }
}