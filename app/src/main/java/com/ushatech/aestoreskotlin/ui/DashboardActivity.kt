package com.ushatech.aestoreskotlin.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.databinding.ActivityMainBinding
import com.ushatech.aestoreskotlin.databinding.HomeSideMenuBinding
import com.ushatech.aestoreskotlin.ui.adapter.DrawerAdapter

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClicks()
        // Please call methods after setContentView for Navigation UI Component
        initNav()
        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        val homeNav: View = binding.drawerHome.getHeaderView(0)
        val homeSideMenuBinding = HomeSideMenuBinding.bind(homeNav)

        homeSideMenuBinding.recvNavDrawer.adapter = DrawerAdapter(this@DashboardActivity)
        homeSideMenuBinding.recvNavDrawer.layoutManager = LinearLayoutManager(this)
        
        homeSideMenuBinding.accountLayout.setOnClickListener { 
            showPopupDrawer(it)
            
        }
        homeSideMenuBinding.ivClose.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)

        }
        homeSideMenuBinding.tvCallUs.setOnClickListener {

            val phone = "+91 8553463261"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }



    }

    private fun showPopupDrawer(it: View?) {
        val popupMenu = PopupMenu(this@DashboardActivity, it)

        // Inflating popup menu from popup_menu.xml file

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.drawer_menu_item, popupMenu.getMenu())
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                // Toast message on menu item clicked

                return true
            }
        })
        // Showing the popup menu
        // Showing the popup menu
        popupMenu.show()
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

        binding.ivNav.setOnClickListener {
            binding.mainLayout.openDrawer(GravityCompat.START)
        }

    }
}