package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {


    private lateinit var binding:ActivitySplashBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(Runnable {
            val dashboardActivityIntent = Intent(this@SplashActivity, DashboardActivity::class.java
            )
            dashboardActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(dashboardActivityIntent)
        },2500)



    }
}