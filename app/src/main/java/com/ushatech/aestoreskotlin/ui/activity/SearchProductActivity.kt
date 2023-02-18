package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivitySearchProductBinding

class SearchProductActivity : BaseActivity() {

    private lateinit var binding:ActivitySearchProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProductBinding.inflate(layoutInflater)
        binding.ivClose.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}