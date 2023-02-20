package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
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
            if(bottomSheetDialog!=null){
                bottomSheetDialog.dismiss()
            }

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
                signupViewModel.registerUserStepTwo(userId.toString(),bottomSheetVerifyOtpBinding.otpView.otp.toString())


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
            return false
        }else if(binding.edtMobileNumber.text.toString().isEmpty()){

            showToast("Please enter mobile number")

            return false
        }else if(binding.edtMobileNumber.text.toString().length<10){
            showToast("Please enter valid number")
            return false
        }else{
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




    }
}