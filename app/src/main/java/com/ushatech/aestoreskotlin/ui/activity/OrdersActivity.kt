package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityOrdersBinding

class OrdersActivity : BaseActivity() {

    private lateinit var binding:ActivityOrdersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)

        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {


    }
}