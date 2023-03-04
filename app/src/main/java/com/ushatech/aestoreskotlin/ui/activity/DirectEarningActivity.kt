package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityDirectEarningBinding

class DirectEarningActivity : BaseActivity() {

    private lateinit var binding:ActivityDirectEarningBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDirectEarningBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = "Direct Earning"
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }




    }
}