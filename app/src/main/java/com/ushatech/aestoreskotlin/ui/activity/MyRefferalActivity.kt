package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityMyRefferalBinding

class MyRefferalActivity : BaseActivity() {


    private lateinit var binding:ActivityMyRefferalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRefferalBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {

        binding.commonTitleBar.tvTitle.text = "My Refferals"
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }




    }
}