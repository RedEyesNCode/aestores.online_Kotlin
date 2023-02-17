package com.ushatech.aestoreskotlin.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseFragment(): Fragment() {

    lateinit var fragmentContext:Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fragmentContext = context
    }
    fun showToast(message:String){

        Toast.makeText(fragmentContext,message,Toast.LENGTH_SHORT).show()

    }

}