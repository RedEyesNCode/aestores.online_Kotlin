package com.ushatech.aestoreskotlin.data.room

import androidx.room.*
import com.ushatech.aestoreskotlin.data.tables.UserCartTable


@Dao
interface UserCartDao {

    @Query("SELECT * FROM user_cart")
    fun getUserCartLocal(): List<UserCartTable>



    // Query for inserting th notes in into the table.
    @Insert
    fun addProductToLocal(vararg user_cart:UserCartTable)

    // Delete note from the table.

    @Delete
    fun deleteCartItem(vararg user_cart: UserCartTable)

    @Update
    fun updateCartItem(vararg user_cart: UserCartTable)



}