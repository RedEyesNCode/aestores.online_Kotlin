package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivitySplashBinding
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant

class SplashActivity : BaseActivity() {


    private lateinit var binding:ActivitySplashBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(Runnable {
            var isLoggedIn = AppSession(this@SplashActivity).getBoolean(Constant.IS_LOGGED_IN)
            if(isLoggedIn){
                val dashboardActivityIntent = Intent(this@SplashActivity, DashboardActivity::class.java
                )
                dashboardActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(dashboardActivityIntent)
            }else{
                val loginActivityIntent = Intent(this@SplashActivity, LoginActivity::class.java
                )
                loginActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(loginActivityIntent)
            }


        },2500)



    }
}