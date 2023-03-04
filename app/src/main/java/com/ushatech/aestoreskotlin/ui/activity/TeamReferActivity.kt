package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityTeamReferBinding

class TeamReferActivity : BaseActivity() {

    private lateinit var binding:ActivityTeamReferBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamReferBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)

    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = "Team Refferals"
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()

        }




    }
}