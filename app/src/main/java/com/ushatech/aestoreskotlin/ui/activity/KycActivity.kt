package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityKycBinding

class KycActivity : BaseActivity() {
    lateinit var binding:ActivityKycBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKycBinding.inflate(layoutInflater)
        initClicks()

        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = getString(R.string.uncompleted_kyc)

        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }





    }
}