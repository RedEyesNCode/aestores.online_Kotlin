package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.data.CartDataRemote
import com.ushatech.aestoreskotlin.databinding.ActivitySignupBinding
import com.ushatech.aestoreskotlin.databinding.BottomSheetVerifyOtpBinding
import com.ushatech.aestoreskotlin.presentation.SignupViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant

class SignupActivity : BaseActivity() {


    lateinit var binding:ActivitySignupBinding
    lateinit var signupViewModel: SignupViewModel
    lateinit var bottomSheetDialog: BottomSheetDialog







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        setContentView(binding.root)
    }

    private fun attachObservers() {
        signupViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        signupViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        signupViewModel.otpSendResponse.observe((this)){
            hideLoader()
            if(it!=null){
                showToast("${it.message}")
            }
            // Also save the User Id Generated into the session of android.
            AppSession(this@SignupActivity).putString(Constant.USER_ID,it.data?.userid.toString())
            showOtpVerificationSheet()
        }
        signupViewModel.signupResponse.observe((this)){

            hideLoader()
            // Navigate to Home Screen after success signup.
            bottomSheetDialog.dismiss()

            // User has siggned in.
            AppSession(this@SignupActivity).put(Constant.IS_LOGGED_IN,true)
            val IntentDashboard = Intent(this@SignupActivity,DashboardActivity::class.java)
            IntentDashboard.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(IntentDashboard)







        }

    }

    private fun showOtpVerificationSheet() {
        val bottomSheetVerifyOtpBinding = BottomSheetVerifyOtpBinding.inflate(LayoutInflater.from(this@SignupActivity))
        bottomSheetDialog =BottomSheetDialog(this,R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetVerifyOtpBinding.root)
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.show()

        bottomSheetVerifyOtpBinding.btnVerifyOtp.setOnClickListener {
            if(bottomSheetVerifyOtpBinding.otpView.otp?.length!! <4){
                showToast("Please enter valid otp")
            }else{
                val userId = AppSession(this@SignupActivity).getString(Constant.USER_ID)
                showLoader()
                // If the user has cart data then send it to backend api.
                if(AppSession(this@SignupActivity).getBoolean(Constant.IS_LOGGED_IN)){
                    val cartData = CartDataRemote()
                    signupViewModel.registerUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString(),cartData)

                }else{
                    signupViewModel.registerUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString(),null)

                }


            }

        }



    }

    private fun setupViewModel() {
        signupViewModel = SignupViewModel()
        signupViewModel = ViewModelProvider(this@SignupActivity).get(SignupViewModel::class.java)
        binding.viewmodel = signupViewModel

    }

    private fun isValidated():Boolean{
        if(binding.edtName.text.toString().isEmpty()){

            showToast("Please enter your name")
            binding.edtName.setError("Please enter your name")

            return false
        }else if(binding.edtMobileNumber.text.toString().isEmpty()){

            showToast("Please enter mobile number")
            binding.edtMobileNumber.setError("Please enter your mobile number")

            return false
        }else if(binding.edtMobileNumber.text.toString().length<10){
            showToast("Please enter valid number")
            binding.edtMobileNumber.setError("Please enter valid mobile number.")
            return false
        }else if(binding.edtEmail.text.toString().isEmpty()){

            showToast("Please enter your email")
            return false

        }else if(!binding.checkBoxOnline.isChecked){
            showToast("Please agree to Terms & Conditions")
            return false
        }else {
            return true
        }



    }

    private fun initClicks() {

        binding.btnSignup.setOnClickListener {
           if(isValidated()){
               showLoader()
               signupViewModel.registerUserStepOne(binding.edtName.text.toString().trim(),binding.edtMobileNumber.text.toString())
           }

        }
        binding.btnLogin.setOnClickListener {
            finish()
        }




    }
}