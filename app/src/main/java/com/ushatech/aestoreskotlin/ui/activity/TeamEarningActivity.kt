package com.ushatech.aestoreskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.databinding.ActivityTeamEarningBinding

class TeamEarningActivity : BaseActivity() {

    lateinit var binding:ActivityTeamEarningBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamEarningBinding.inflate(layoutInflater)

        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = getString(R.string.team_earning)
        binding.commonTitleBar.ivClose.setOnClickListener {
            finish()
        }




    }
}