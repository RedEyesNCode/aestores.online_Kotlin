package com.ushatech.aestoreskotlin.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.data.CategorySubCategoryData
import com.ushatech.aestoreskotlin.data.MasterCategoryModel
import com.ushatech.aestoreskotlin.data.SubSuperCategoryData
import com.ushatech.aestoreskotlin.data.room.AppDatabase
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.ActivityMainBinding
import com.ushatech.aestoreskotlin.databinding.CategorySideMenuBinding
import com.ushatech.aestoreskotlin.databinding.HomeSideMenuBinding
import com.ushatech.aestoreskotlin.presentation.DashboardViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant
import com.ushatech.aestoreskotlin.ui.adapter.DrawerAdapter
import com.ushatech.aestoreskotlin.ui.adapter.ImageViewPagerTrendingAdapter
import com.ushatech.aestoreskotlin.ui.adapter.RoomCartAdapter
import com.ushatech.aestoreskotlin.ui.fragments.*
import com.ushatech.aestoreskotlin.uitls.FragmentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DashboardActivity : BaseActivity(),ImageViewPagerTrendingAdapter.onEventTrendingViewPager,DrawerAdapter.onEvent {

    private lateinit var binding:ActivityMainBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun categoryClick(position: Int, categoryId: String) {
        showLog("Category is clicked.")
    }

    override fun onShowCategoryProducts(position: Int, categoryId: String) {
        binding.mainLayout.closeDrawer(GravityCompat.START)
        FragmentUtils().addFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,
            CategoryProductFragment.newInstance(categoryId,""),
            CategoryProductFragment::class.java.canonicalName,false)

    }

    override fun onProductClick(position: Int, productId: String) {
        FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,
            ProductDetailFragment.newInstance(productId,""),
            ProductDetailFragment::class.java.canonicalName,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClicks()
        // Please call methods after setContentView for Navigation UI Component
        initNav()
        setupViewModel()
        attachObservers()
        intialApiCall()

        setupNavigationDrawer()
    }

    private fun intialApiCall() {
        dashboardViewModel.getAllCategory()
    }

    private fun attachObservers() {
        dashboardViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        dashboardViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        dashboardViewModel.categoryResponse.observe((this)){
            hideLoader()
            setupCategoryInSideBar(it)


        }
    }

    private fun setupCategoryInSideBar(it: AllCategoryResponse?) {
        val homeNav: View = binding.drawerHome.getHeaderView(0)
        val homeSideMenuBinding = HomeSideMenuBinding.bind(homeNav)

        val  subCategoriesSubAdapter = ArrayList<AllCategoryResponse.Subcategories>()

        val subCategoryDataMutableList : MutableList<CategorySubCategoryData> =  mutableListOf()
        val subSuperCategoryDataMutableList : MutableList<SubSuperCategoryData> =  mutableListOf()
        val masterCategoryModelMutableList :MutableList<MasterCategoryModel> = mutableListOf()
        for(index in it!!.data.indices){
            // Adding All the Category Views.
            for(subcategories in it.data.get(index).subcategories){
                val subSuperCategoryData = SubSuperCategoryData(subcategories.name.toString(),subcategories.supercategories)
                subSuperCategoryDataMutableList.add(subSuperCategoryData)
                subCategoriesSubAdapter.add(subcategories)
//                masterCategoryModelMutableList.add(MasterCategoryModel(MasterCategoryModel.SUB_CATEGORY,subSuperCategoryData,false))

            }
        }
        for (category in it.data){
            val categorySubCategoryData = CategorySubCategoryData(category.name.toString(),subSuperCategoryDataMutableList)
            subCategoryDataMutableList.add(categorySubCategoryData)
            masterCategoryModelMutableList.add(MasterCategoryModel(MasterCategoryModel.CATEGORY,category,true))
        }

        for(index in it!!.data.indices){
            // Adding All the Category Views.
            for(subcategories in it.data.get(index).subcategories){
                val subSuperCategoryData = SubSuperCategoryData(subcategories.name.toString(),subcategories.supercategories)
                subSuperCategoryDataMutableList.add(subSuperCategoryData)
                masterCategoryModelMutableList.add(MasterCategoryModel(MasterCategoryModel.SUB_CATEGORY,subcategories,false))

            }
        }


        homeSideMenuBinding.recvNavDrawer.adapter = DrawerAdapter(this@DashboardActivity,it!!,subCategoriesSubAdapter,masterCategoryModelMutableList,this)
        homeSideMenuBinding.recvNavDrawer.layoutManager = LinearLayoutManager(this)



    }

    private fun setupViewModel() {
        dashboardViewModel = DashboardViewModel()
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding.viewmodel = dashboardViewModel


    }


    private fun setupNavigationDrawer() {
        val homeNav: View = binding.drawerHome.getHeaderView(0)
        binding.mainLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val homeSideMenuBinding = HomeSideMenuBinding.bind(homeNav)
        val userData =  AppSession(this@DashboardActivity).getUser()
        if("${userData?.data?.name}"==null){
            homeSideMenuBinding.tvName.text = "Please Login"

        }else{
            homeSideMenuBinding.tvName.text = "Hi, ${userData?.data?.name}"


        }
        setupNavClicks(homeSideMenuBinding)
        initMyAccountClicks(homeSideMenuBinding)
        initHelpLayoutClicks(homeSideMenuBinding)
        if(AppSession(this@DashboardActivity).getBoolean(Constant.IS_LOGGED_IN)){
            homeSideMenuBinding.btnLogout.visibility = View.VISIBLE
        }else{
            homeSideMenuBinding.btnLogout.visibility = View.GONE

        }



        homeSideMenuBinding.btnLogout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@DashboardActivity)
            alertDialog.setTitle("Logout from App ?")
            alertDialog.setCancelable(true)
            alertDialog.setPositiveButton("Yes",{dialog, which ->
                run {
                    AppSession(this@DashboardActivity).clear()
                    deleteUserCartRoom()
                }
            })

            alertDialog.setNegativeButton("CANCEL",null)
            alertDialog.create()

            alertDialog.show()



        }


    }
    private fun deleteUserCartRoom() {
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                this@DashboardActivity, AppDatabase::class.java, "aestores_online.db"
            ).build()
            // Make the cart Object and store in Room Db


            db.userCartDao().deletUserCart()
            val cartDataLocal = db.userCartDao().getUserCartLocal()
            launch(Dispatchers.Main) {
                showToast("Logged Out !")
                val loginIntent = Intent(this@DashboardActivity,DashboardActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(loginIntent)

            }



        }


    }

    private fun initMyAccountClicks(homeSideMenuBinding: HomeSideMenuBinding) {

        homeSideMenuBinding.myAccountLayout.setOnClickListener {

            if(homeSideMenuBinding.dropDownMyAccount.visibility==View.VISIBLE){
                homeSideMenuBinding.ivDownAccount.visibility = View.VISIBLE
                homeSideMenuBinding.ivUpAccount.visibility = View.GONE
                homeSideMenuBinding.dropDownMyAccount.visibility = View.GONE
            }else{
                homeSideMenuBinding.ivDownAccount.visibility = View.GONE
                homeSideMenuBinding.ivUpAccount.visibility = View.VISIBLE

                homeSideMenuBinding.dropDownMyAccount.visibility = View.VISIBLE
            }

        }
        homeSideMenuBinding.wishlistLayout.setOnClickListener {

            binding.bottomNavigationbar.selectedItemId = R.id.wishListFragment

            binding.mainLayout.closeDrawer(GravityCompat.START)

        }
        homeSideMenuBinding.infoSettingsLayout.setOnClickListener {

            binding.bottomNavigationbar.selectedItemId = R.id.profileFragment
            binding.mainLayout.closeDrawer(GravityCompat.START)


        }
        homeSideMenuBinding.myPurchaseLayout.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,OrdersActivity::class.java))



        }
        homeSideMenuBinding.myBalanceLayout.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,BalanceActivity::class.java))
        }
        homeSideMenuBinding.earningLayout.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,EarningActivity::class.java))

        }

        // My Team Layout clicks are placed here.
        homeSideMenuBinding.myrefferals.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,MyRefferalActivity::class.java))
        }

        homeSideMenuBinding.teamRefferals.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,TeamReferActivity::class.java))

        }

        homeSideMenuBinding.tvDirectEarn.setOnClickListener {

            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,DirectEarningActivity::class.java))

        }

        homeSideMenuBinding.myTeamLayout.setOnClickListener {

            if(homeSideMenuBinding.TeamLinearLayout.visibility==View.VISIBLE){
                homeSideMenuBinding.ivDownTeam.visibility = View.VISIBLE
                homeSideMenuBinding.ivUpTeam.visibility = View.GONE
                homeSideMenuBinding.TeamLinearLayout.visibility = View.GONE
            }else{
                homeSideMenuBinding.ivDownTeam.visibility = View.GONE
                homeSideMenuBinding.ivUpTeam.visibility = View.VISIBLE

                homeSideMenuBinding.TeamLinearLayout.visibility = View.VISIBLE
            }


        }

        homeSideMenuBinding.tvTeamEarn.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)

            startActivity(Intent(this@DashboardActivity,TeamEarningActivity::class.java))
        }

        homeSideMenuBinding.myOffers.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,OffersActivity::class.java))
        }
        homeSideMenuBinding.unfinishedKyc.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)

            startActivity(Intent(this@DashboardActivity,KycActivity::class.java))

        }






    }

    private fun initHelpLayoutClicks(homeSideMenuBinding: HomeSideMenuBinding) {

        homeSideMenuBinding.helpInfoLayout.setOnClickListener {
            if(homeSideMenuBinding.dropDownHelpInfo.visibility==View.VISIBLE){
                homeSideMenuBinding.ivDown.visibility = View.VISIBLE
                homeSideMenuBinding.ivUp.visibility = View.GONE
                homeSideMenuBinding.dropDownHelpInfo.visibility = View.GONE
            }else{
                homeSideMenuBinding.ivDown.visibility = View.GONE
                homeSideMenuBinding.ivUp.visibility = View.VISIBLE

                homeSideMenuBinding.dropDownHelpInfo.visibility = View.VISIBLE
            }
        }
        homeSideMenuBinding.tvPrivacyPolicy.setOnClickListener {
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

        homeSideMenuBinding.exchangeLayout.setOnClickListener {
            val url = getString(R.string.EXCHANGE_POLICY)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        homeSideMenuBinding.shippingLayout.setOnClickListener {
            val url = getString(R.string.SHIPPING_POLICY)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        homeSideMenuBinding.aboutUsLayout.setOnClickListener {
            val url = getString(R.string.ABOUT_US)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)


        }
    }

    private fun setupNavClicks(homeSideMenuBinding: HomeSideMenuBinding) {
        homeSideMenuBinding.ivClose.setOnClickListener {
            binding.mainLayout.closeDrawer(GravityCompat.START)

        }
    }


    fun showPopupDrawer(it: View?) {
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

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){

            val alertDialog = AlertDialog.Builder(this@DashboardActivity)
            alertDialog.setTitle("Exit App ?")
            alertDialog.setCancelable(true)
            alertDialog.setPositiveButton("Yes",{dialog, which ->
                run {

                    finish()


                }
            })
            alertDialog.setNegativeButton("CANCEL",null)
            alertDialog.create()

            alertDialog.show()
        }else{
            super.onBackPressed()
        }

    }


    private fun initNav() {

        val bottomNavigationView = binding.bottomNavigationbar
        FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,HomeFragment(),HomeFragment::class.java.canonicalName,false)


        // Using manual clicks to test along with webview in android.
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if(item.itemId==R.id.wishListFragment){
                    val fm: FragmentManager = supportFragmentManager
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,WishlistFragment(),WishlistFragment::class.java.canonicalName,false)

                }else if(item.itemId==R.id.homeFragment){
                    // ADDING CHECKS FOR THE BACK STACK MANAGE.
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,HomeFragment(),HomeFragment::class.java.canonicalName,false)

                }else if(item.itemId==R.id.categoryFragment){
//                    binding.mainLayout.openDrawer(binding.drawerCategory)
                    val fm: FragmentManager = supportFragmentManager
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,CategoryFragment(),CategoryFragment::class.java.canonicalName,false)

                }else if(item.itemId==R.id.profileFragment){
                    val fm: FragmentManager = supportFragmentManager
                    FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,ProfileFragment(),ProfileFragment::class.java.canonicalName,false)

                }else if(item.itemId==R.id.cartFragment){
                    if(AppSession(this@DashboardActivity).getBoolean(Constant.IS_LOGGED_IN)){
                        FragmentUtils().replaceFragmentBackStack(supportFragmentManager,
                            com.ushatech.aestoreskotlin.R.id.activity_main_nav_host_fragment,CartFragment.newInstance("local","false"),CartFragment::class.java.canonicalName,true)
                    }else{
                        FragmentUtils().replaceFragmentBackStack(supportFragmentManager,
                            com.ushatech.aestoreskotlin.R.id.activity_main_nav_host_fragment,CartFragment.newInstance("local","true"),CartFragment::class.java.canonicalName,true)
                    }



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
            val searchIntent = Intent(this@DashboardActivity, SearchProductActivity::class.java)
            startActivityForResult(searchIntent,77)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==77){
                // load the product details fragment
                val productId = data?.getStringExtra("PRODUCT_ID")
//                showToast(productId.toString())
                FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,
                            ProductDetailFragment.newInstance(productId.toString(),""),
                            ProductDetailFragment::class.java.canonicalName,false)


            }
        }

    }
}