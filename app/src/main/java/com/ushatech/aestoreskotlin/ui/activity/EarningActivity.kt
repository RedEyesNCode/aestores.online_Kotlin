package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityEarningBinding

class EarningActivity : BaseActivity() {

    private lateinit var binding:ActivityEarningBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEarningBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = "My Earnings"
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }


    }
}