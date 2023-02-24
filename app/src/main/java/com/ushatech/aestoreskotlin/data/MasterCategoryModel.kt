package com.ushatech.aestoreskotlin.data

import androidx.annotation.IntDef

class MasterCategoryModel {

    companion object {

        @IntDef(CATEGORY, SUB_CATEGORY, SUPER_CATEGORY)
        @Retention(AnnotationRetention.SOURCE)
        annotation class RowType

        const val CATEGORY = 1
        const val SUB_CATEGORY = 2
        const val SUPER_CATEGORY = 3
    }

    @RowType
    var type:Int

    lateinit var category : AllCategoryResponse.Data

    lateinit var subCategory : AllCategoryResponse.Subcategories


    var isExpanded : Boolean

    constructor(@RowType type : Int, category : AllCategoryResponse.Data, isExpanded : Boolean = false){
        this.type = type
        this.category = category
        this.isExpanded = isExpanded
    }
    constructor(@RowType type : Int, subCategory : AllCategoryResponse.Subcategories, isExpanded : Boolean = false){
        this.type = type
        this.subCategory = subCategory
        this.isExpanded = isExpanded
    }

}