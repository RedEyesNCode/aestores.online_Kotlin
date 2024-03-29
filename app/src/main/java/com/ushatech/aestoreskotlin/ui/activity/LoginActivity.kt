package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.data.CartDataRemote
import com.ushatech.aestoreskotlin.data.room.AppDatabase
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.ActivityLoginBinding
import com.ushatech.aestoreskotlin.databinding.BottomSheetVerifyOtpBinding
import com.ushatech.aestoreskotlin.presentation.LoginViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {


    lateinit var binding:ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    lateinit var bottomSheetDialog: BottomSheetDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        setContentView(binding.root)
    }

    private fun attachObservers() {

        loginViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        loginViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }

        loginViewModel.otpSendResponse.observe((this)){
            hideLoader()
            if(it!=null){
                showToast("${it.message}")
            }
            // Also save the User Id Generated into the session of android.
            AppSession(this@LoginActivity).putString(Constant.USER_ID,it.data?.userid.toString())
            showOtpVerificationSheet()



        }
        loginViewModel.loginUserResponse.observe((this)){
            hideLoader()
            if(it!=null){
                AppSession(this@LoginActivity).put(Constant.IS_LOGGED_IN,true)
                AppSession(this@LoginActivity).putObject(Constant.USER_INFO,it)
                val IntentDashboard = Intent(this@LoginActivity,DashboardActivity::class.java)
                IntentDashboard.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(IntentDashboard)
            }else{
                showToast(Constant.OOPS_SW)
            }




        }



    }
    private fun showOtpVerificationSheet() {
        val bottomSheetVerifyOtpBinding = BottomSheetVerifyOtpBinding.inflate(LayoutInflater.from(this@LoginActivity))
        bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetVerifyOtpBinding.root)
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.show()
        var cartLocal:List<UserCartTable>
        var cartDataRemotes = ArrayList<CartDataRemote>()
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                this@LoginActivity, AppDatabase::class.java, "aestores_online.db"
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
                val userId = AppSession(this@LoginActivity).getString(Constant.USER_ID)
                showLoader()
                if(AppSession(this@LoginActivity).getBoolean(Constant.IS_LOGGED_IN)){
                    // send the local cart daqta to the backend api.
                    loginViewModel.loginUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString(),cartDataRemotes)
                }else{
                    loginViewModel.loginUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString(),null)

                }



            }

        }



    }
    private fun setupViewModel() {
        loginViewModel = LoginViewModel()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewmodel = loginViewModel



    }

    private fun initClicks() {
        binding.btnLogin.setOnClickListener {
            if(isValidated()){
                showLoader()
                loginViewModel.loginUserStepOne(binding.edtMobileNumber.text.toString().trim())
            }
        }

        binding.btnSignup.setOnClickListener {

            val signupIntent = Intent(this@LoginActivity,SignupActivity::class.java)
            startActivity(signupIntent)



        }



    }

    private fun isValidated(): Boolean {
        if(binding.edtMobileNumber.text.toString().isEmpty()){

            showToast("Please enter mobile number.")
            binding.edtMobileNumber.setError("Please enter mobile number.")
            return false
        }else if(binding.edtMobileNumber.text.toString().length<10){
            showToast("Please enter valid mobile number")
            binding.edtMobileNumber.setError("Please enter valid mobile number.")
            return false

        }else{
            return true
        }


    }
}