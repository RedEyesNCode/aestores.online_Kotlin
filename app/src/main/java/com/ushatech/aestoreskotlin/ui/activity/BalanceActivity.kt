package com.ushatech.aestoreskotlin.ui.activity

import android.os.Bundle
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityBalanceBinding

class BalanceActivity : BaseActivity() {

    private lateinit var binding:ActivityBalanceBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBalanceBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }

        binding.commonTitleBar.tvTitle.text = "My Balance"


    }
}