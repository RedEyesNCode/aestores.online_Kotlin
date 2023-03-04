package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityOffersBinding

class OffersActivity : BaseActivity() {

    lateinit var binding:ActivityOffersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOffersBinding.inflate(layoutInflater)

        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = getString(R.string.offers_)
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }





    }
}