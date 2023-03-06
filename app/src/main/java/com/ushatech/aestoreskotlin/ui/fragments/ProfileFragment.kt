package com.ushatech.aestoreskotlin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.intuit.sdp.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.databinding.BottomSheetVerifyOtpBinding
import com.ushatech.aestoreskotlin.databinding.FragmentProfileBinding
import com.ushatech.aestoreskotlin.presentation.ProfileViewModel
import com.ushatech.aestoreskotlin.presentation.WishListViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant
import com.ushatech.aestoreskotlin.ui.activity.DashboardActivity
import com.ushatech.aestoreskotlin.ui.activity.SignupActivity
import com.ushatech.aestoreskotlin.ui.adapter.ViewPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentProfileBinding
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var viewModel:ProfileViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        checkLoginState()
        initClicks()
        setupViewModel()
        attachObservers()

        setupTabLayout()
        return binding.root
    }

    private fun attachObservers() {
        viewModel.isFailed.observe((viewLifecycleOwner)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        viewModel.isSuccess.observe((viewLifecycleOwner)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        viewModel.otpSendResponse.observe((viewLifecycleOwner)){
            hideLoader()
            if(it!=null){
                showToast("${it.message}")
            }
            // Also save the User Id Generated into the session of android.
            AppSession(fragmentContext).putString(Constant.USER_ID,it.data?.userid.toString())
            showOtpVerificationSheet()



        }
        viewModel.loginUserResponse.observe((viewLifecycleOwner)){
            hideLoader()
            if(it!=null){
                AppSession(fragmentContext).put(Constant.IS_LOGGED_IN,true)
                AppSession(fragmentContext).putObject(Constant.USER_INFO,it)
                showToastLong("Login User Successfully ")
                val IntentDashboard = Intent(fragmentContext, DashboardActivity::class.java)
                IntentDashboard.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(IntentDashboard)
            }else{
                showToast(Constant.OOPS_SW)
            }




        }

    }
    private fun showOtpVerificationSheet() {
        val bottomSheetVerifyOtpBinding = BottomSheetVerifyOtpBinding.inflate(LayoutInflater.from(fragmentContext))
        bottomSheetDialog = BottomSheetDialog(fragmentContext, com.ushatech.aestoreskotlin.R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetVerifyOtpBinding.root)
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.show()

        bottomSheetVerifyOtpBinding.btnVerifyOtp.setOnClickListener {
            if(bottomSheetVerifyOtpBinding.otpView.otp?.length!! <4){
                showToast("Please enter valid otp")
            }else{
                val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
                showLoader()
                viewModel.loginUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString())


            }

        }



    }

    private fun setupViewModel() {
        viewModel = ProfileViewModel()
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel

    }

    private fun checkLoginState() {

        val isLoggedIn = AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)
        if(isLoggedIn){
            // show the edit profile screen
            binding.tabLayout.visibility = View.VISIBLE
            binding.tabViewpager.visibility = View.VISIBLE
            binding.loginLayout.root.visibility = View.GONE


        }else{

            binding.tabLayout.visibility = View.GONE
            binding.tabViewpager.visibility = View.GONE
            binding.loginLayout.root.visibility = View.VISIBLE

        }

    }

    private fun setupTabLayout() {
        val dashboardTab = binding.tabLayout.newTab()
        dashboardTab.text = "Dashboard"
        dashboardTab.select()

        binding.tabLayout.addTab(dashboardTab)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Edit Profile"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("User referrals"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Learning program"))
        val viewPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.tabViewpager.setAdapter(viewPagerAdapter)
        TabLayoutMediator(binding.tabLayout, binding.tabViewpager) { tab, position ->
            if(position==0){
                tab.setText("Dashboard")
            }else if(position==1){
                tab.setText("Edit Profile")
            }else if(position==2){
                tab.setText("User referrals")
            }else if(position==3){
                tab.setText("Learning program")
            }

        }.attach()
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(
                resources.getDimensionPixelOffset(R.dimen._5sdp),
                resources.getDimensionPixelOffset(
                    R.dimen._8sdp
                ),
                resources.getDimensionPixelOffset(R.dimen._5sdp),
                resources.getDimensionPixelOffset(
                    R.dimen._8sdp
                )
            )
            tab.requestLayout()
        }
    }

    private fun initClicks() {
        binding.loginLayout.btnSignup.setOnClickListener {
            val signupIntent = Intent(fragmentContext, SignupActivity::class.java)
            startActivity(signupIntent)
        }
        binding.loginLayout.btnLogin.setOnClickListener {
            if(binding.loginLayout.edtMobileNumber.text.toString().isEmpty()){
                binding.loginLayout.edtMobileNumber.setError("Please enter your mobile number.")
            }else{
                // Call the login api and refresh the dashboard activity.
                showLoader()
                viewModel.loginUserStepOne(binding.loginLayout.edtMobileNumber.text.toString())

            }

        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}