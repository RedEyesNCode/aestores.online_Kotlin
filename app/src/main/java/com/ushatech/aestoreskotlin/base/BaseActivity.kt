package com.ushatech.aestoreskotlin.base

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

open class BaseActivity():AppCompatActivity() {

    lateinit var commonProgressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        commonProgressDialog = ProgressDialog(this)
        commonProgressDialog.setCancelable(false)
        commonProgressDialog.setCanceledOnTouchOutside(false)
        commonProgressDialog.setTitle("Please wait")
        commonProgressDialog.setMessage("Loading....")

        // Disable night theme for the entire app.

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


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

    fun showToast(message:String){

        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    }
}