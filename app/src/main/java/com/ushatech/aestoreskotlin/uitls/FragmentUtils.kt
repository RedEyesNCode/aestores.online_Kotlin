package com.ushatech.aestoreskotlin.uitls

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class FragmentUtils {
    private val TAG = "FragmentUtils"
    fun replaceFragmentBackStack(
        fragmentManager: FragmentManager,
        container: Int,
        fragment: Fragment?,
        tag: String?,
        isAddToBackStack: Boolean
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(container, fragment!!)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commit()
    }


    fun addFragmentBackStack(
        fragmentManager: FragmentManager,
        containerID: Int,
        fragment: Fragment?,
        tag: String?,
        isAddToBackStack: Boolean
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerID, fragment!!)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }
}