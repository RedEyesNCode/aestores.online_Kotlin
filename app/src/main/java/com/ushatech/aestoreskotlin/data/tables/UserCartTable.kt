package com.ushatech.aestoreskotlin.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cart")
data class UserCartTable(

    @PrimaryKey(autoGenerate = true)
    var note_id:Int,

    @ColumnInfo(name = "productId") var productId:String,
    @ColumnInfo(name = "quantity") var quantity:String,
    @ColumnInfo(name = "userId") var userId:String,
    @ColumnInfo(name = "productName") var productName:String,
    @ColumnInfo(name = "imageUrl") var imageUrl:String,
    @ColumnInfo(name = "priceTotal") var priceTotal:String,
    @ColumnInfo(name = "priceProduct") var priceProduct:String,


)
