package com.ushatech.aestoreskotlin.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseFragment(): Fragment() {
    lateinit var commonProgressDialog: ProgressDialog



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        commonProgressDialog = ProgressDialog(fragmentContext)
        commonProgressDialog.setCancelable(false)
        commonProgressDialog.setCanceledOnTouchOutside(false)
        commonProgressDialog.setTitle("Please wait")
        commonProgressDialog.setMessage("Loading....")

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    lateinit var fragmentContext:Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fragmentContext = context
        commonProgressDialog = ProgressDialog(fragmentContext)
        commonProgressDialog.setCancelable(false)
        commonProgressDialog.setCanceledOnTouchOutside(false)
        commonProgressDialog.setTitle("Please wait")
        commonProgressDialog.setMessage("Loading....")
    }
    fun showToast(message:String){

        Toast.makeText(fragmentContext,message,Toast.LENGTH_SHORT).show()

    }
    fun showToastLong(message: String){
        Toast.makeText(fragmentContext,message,Toast.LENGTH_LONG).show()

    }
    fun showLoader(){
        commonProgressDialog.show()

    }
    public fun showLog(message:String){
        Log.i("DEV_ASHUTOSH", "showLog: $message")


    }

    fun hideLoader(){
        commonProgressDialog.hide()


    }
}