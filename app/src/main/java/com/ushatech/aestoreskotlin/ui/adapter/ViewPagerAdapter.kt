package com.ushatech.aestoreskotlin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ushatech.aestoreskotlin.ui.fragments.LearningProfileFragment
import com.ushatech.aestoreskotlin.ui.fragments.UserRefferalFragment
import com.ushatech.aestoreskotlin.ui.fragments.profileFragments.DashboardProfileFragment
import com.ushatech.aestoreskotlin.ui.fragments.profileFragments.EditProfileFragment

class ViewPagerAdapter: FragmentStateAdapter {
    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)


    override fun createFragment(position: Int): Fragment {
        if(position==0){
            return DashboardProfileFragment()
        }else if(position==1){
            return EditProfileFragment()

        }else if(position==2){
            return UserRefferalFragment()

        }else if(position==3){
            return LearningProfileFragment()
        }else{
            return DashboardProfileFragment()

        }



    }

    override fun getItemCount(): Int {
        return 4
    }
}