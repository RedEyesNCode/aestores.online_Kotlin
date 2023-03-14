package com.ushatech.aestoreskotlin.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.CartDataRemote
import com.ushatech.aestoreskotlin.data.room.AppDatabase
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.BottomSheetVerifyOtpBinding
import com.ushatech.aestoreskotlin.databinding.FragmentWishlistBinding
import com.ushatech.aestoreskotlin.presentation.WishListViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant
import com.ushatech.aestoreskotlin.ui.activity.DashboardActivity
import com.ushatech.aestoreskotlin.ui.activity.SignupActivity
import com.ushatech.aestoreskotlin.ui.adapter.WishlistAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WishlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WishlistFragment : BaseFragment(),WishlistAdapter.onEventWishlistAdapter {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentWishlistBinding
    private lateinit var viewModel:WishListViewModel
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onDeleteClick() {

        showToastLong("Perform Delete Operation.")
    }

    override fun onProductClick() {
        showToastLong("Clicked on Product")
    }

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

        binding = FragmentWishlistBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
//        binding.mainWebView.loadUrl("https://aestores.online/login")

        // Setting up basic adapter
        checkLoginState()
        initClicks()
        setupViewModel()
        attachObservers()



        return binding.root
    }

    private fun setupViewModel() {
        viewModel = WishListViewModel()
        viewModel = ViewModelProvider(this).get(WishListViewModel::class.java)
        binding.viewmodel = viewModel

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
        bottomSheetDialog = BottomSheetDialog(fragmentContext,R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetVerifyOtpBinding.root)
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.show()

        var cartLocal:List<UserCartTable>
        var cartDataRemotes = ArrayList<CartDataRemote>()
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                fragmentContext, AppDatabase::class.java, "aestores_online.db"
            ).build()
            cartDataRemotes = ArrayList<CartDataRemote>()
            cartLocal = db.userCartDao().getUserCartLocal()
            for (cart in cartLocal){
                cartDataRemotes.add(CartDataRemote(cart.productId.toInt(),cart.quantity.toInt()))
            }
        }

        bottomSheetVerifyOtpBinding.btnVerifyOtp.setOnClickListener {
            if(bottomSheetVerifyOtpBinding.otpView.otp?.length!! <4){
                showToast("Please enter valid otp")
            }else{
                val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
                showLoader()

                if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){
                    viewModel.loginUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString(),cartDataRemotes)
                }else{
                    viewModel.loginUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString(),null)

                }



            }

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

    private fun checkLoginState() {
        val isLoggedIn = AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)
        if(isLoggedIn){

            binding.recvWishlist.adapter =WishlistAdapter(fragmentContext,this)
            binding.recvWishlist.layoutManager= GridLayoutManager(fragmentContext,2)

            binding.recvWishlist.visibility = View.VISIBLE
            binding.loginLayout.root.visibility = View.GONE
        }else{
            binding.recvWishlist.visibility = View.GONE
            binding.loginLayout.root.visibility = View.VISIBLE

        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WishlistFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WishlistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}