package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityMainBinding
import com.ushatech.aestoreskotlin.databinding.CategorySideMenuBinding
import com.ushatech.aestoreskotlin.databinding.HomeSideMenuBinding
import com.ushatech.aestoreskotlin.ui.adapter.DrawerAdapter
import com.ushatech.aestoreskotlin.ui.fragments.CartFragment
import com.ushatech.aestoreskotlin.ui.fragments.HomeFragment
import com.ushatech.aestoreskotlin.ui.fragments.ProfileFragment
import com.ushatech.aestoreskotlin.ui.fragments.WishlistFragment
import com.ushatech.aestoreskotlin.uitls.FragmentUtils

class DashboardActivity : BaseActivity() {

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
        val categoryNav :View = binding.drawerCategory.getHeaderView(0)

        binding.mainLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        val categorySideMenuBinding = CategorySideMenuBinding.bind(categoryNav)
        val homeSideMenuBinding = HomeSideMenuBinding.bind(homeNav)

        homeSideMenuBinding.recvNavDrawer.adapter = DrawerAdapter(this@DashboardActivity)
        homeSideMenuBinding.recvNavDrawer.layoutManager = LinearLayoutManager(this)
        categorySideMenuBinding.recvCategoryDrawer.adapter = DrawerAdapter(this@DashboardActivity)
        categorySideMenuBinding.recvCategoryDrawer.layoutManager = LinearLayoutManager(this)
        homeSideMenuBinding.accountLayout.setOnClickListener { 
            showPopupDrawer(it)
            
        }
        homeSideMenuBinding.ivClose.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)

        }





        homeSideMenuBinding.tvPrivacyPolicy.setOnClickListener {

//            val phone = "+91 8553463261"
//            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
//            startActivity(intent)
            val url = getString(R.string.PRIVACY_POLICY)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }
        homeSideMenuBinding.tvTerms.setOnClickListener {
            val url = getString(R.string.TERMS_AND_CONDITIONS)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }

        categorySideMenuBinding.ivClose.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.END)
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
//        val navController: NavController =
//            Navigation.findNavController(
//                this@DashboardActivity,
//                R.id.activity_main_nav_host_fragment
//            )
        val bottomNavigationView = binding.bottomNavigationbar
        FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,HomeFragment(),HomeFragment::class.java.canonicalName,false)


        bottomNavigationView.getOrCreateBadge(R.id.cartFragment).number = 2
        // Using manual clicks to test along with webview in android.
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if(item.itemId==R.id.categoryFragment){
                    binding.mainLayout.openDrawer(binding.drawerCategory)
                }else if(item.itemId==R.id.wishListFragment){
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,WishlistFragment(),WishlistFragment::class.java.canonicalName,false)

                }else if(item.itemId==R.id.homeFragment){
                    // ADDING CHECKS FOR THE BACK STACK MANAGE.
                    if(supportFragmentManager.backStackEntryCount==1){
                        supportFragmentManager.popBackStack()
                        // Means only CategoryProduct stack is present.
                    }else if(supportFragmentManager.backStackEntryCount==2){
                        //Means CategoryProduct , Product details stacks are present
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.popBackStack()
                    }else if(supportFragmentManager.backStackEntryCount==3){
                        //Means CategoryProduct , Product details, Cart screens (3) stacks are present
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.popBackStack()
                    }else{
                        FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,HomeFragment(),HomeFragment::class.java.canonicalName,false)

                    }



                }else if(item.itemId==R.id.categoryFragment){
                    binding.mainLayout.openDrawer(binding.drawerCategory)
                }else if(item.itemId==R.id.profileFragment){
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,ProfileFragment(),ProfileFragment::class.java.canonicalName,false)

                }else if(item.itemId==R.id.cartFragment){
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,CartFragment(),CartFragment::class.java.canonicalName,false)

                }


                return true
            }
        })


    }

    private fun initClicks() {

        binding.ivNav.setOnClickListener {
            binding.mainLayout.openDrawer(GravityCompat.START)
        }
        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SearchProductActivity::class.java))
        }

    }
}