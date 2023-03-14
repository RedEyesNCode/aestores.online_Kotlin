package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.data.MasterCategoryModel
import com.ushatech.aestoreskotlin.databinding.CategoryItemBinding
import com.ushatech.aestoreskotlin.databinding.SubcategoryItemBinding
import com.ushatech.aestoreskotlin.presentation.DashboardViewModel


class DrawerAdapter(
    var context: Context,
    var allCategoryResponse: AllCategoryResponse,
    var subCategories: ArrayList<AllCategoryResponse.Subcategories>,
    var masterCategoryModel:MutableList<MasterCategoryModel>,
    var onEventActivity:DrawerAdapter.onEvent
) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var categoryBinding: CategoryItemBinding
    lateinit var subCategoryBinding :SubcategoryItemBinding
    private var actionLock = false



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        categoryBinding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        subCategoryBinding = SubcategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        val viewHolder:RecyclerView.ViewHolder = when(viewType){
            MasterCategoryModel.CATEGORY -> CategoryViewHolder(categoryBinding)
            MasterCategoryModel.SUB_CATEGORY -> SubCategoryViewHolder(subCategoryBinding)
            else -> CategoryViewHolder(categoryBinding)

        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val masterCategoryModel = masterCategoryModel.get(position)

        when(masterCategoryModel.type){
            MasterCategoryModel.CATEGORY ->{
                (holder as CategoryViewHolder).categoryItemBinding.tvCategoryName.text = masterCategoryModel.category.name


                holder.categoryItemBinding.tvCategoryName.setOnClickListener {
                    //navigate to categoryProductFragment with categoryId.

                    onEventActivity.onShowCategoryProducts(position,masterCategoryModel.category.id.toString())


                }


                holder.categoryItemBinding.ivUp.setOnClickListener {
                    holder.categoryItemBinding.ivUp.visibility = View.GONE
                    holder.categoryItemBinding.ivDown.visibility = View.VISIBLE
                    holder.categoryItemBinding.recvSubCategory.visibility =View.GONE
                    //CLOSE

                }
                holder.categoryItemBinding.ivDown.setOnClickListener {
                    holder.categoryItemBinding.ivUp.visibility = View.VISIBLE
                    holder.categoryItemBinding.ivDown.visibility = View.GONE
                    holder.categoryItemBinding.recvSubCategory.visibility =View.VISIBLE
                    // EXPAND
                    // Filter the subCategory list. for the certain category ID.
                    val dashboardViewModel = DashboardViewModel()
                    dashboardViewModel.getAllSubCategory(masterCategoryModel.category.id.toString())
                    dashboardViewModel.subCategoryResponse.observe((context as LifecycleOwner),{
                        if(it!=null){
                            Log.i("DEV_ASHUTOSH", "showLog: SET SUB ADAPTER")

                            (holder as CategoryViewHolder).categoryItemBinding.recvSubCategory.adapter =SubcategoryAdapter(context,it.data,it.data.size,masterCategoryModel,onEventActivity)
                            (holder as CategoryViewHolder).categoryItemBinding.recvSubCategory.layoutManager =LinearLayoutManager(context)
                        }
                    })
                    





                }


            }
            MasterCategoryModel.SUB_CATEGORY->{
                (holder as SubCategoryViewHolder).subcategoryBinding.tvSubcategoryName.text = masterCategoryModel.subCategory.name
            }
        }



    }

    public interface onEvent{
        fun categoryClick(position: Int,categoryId:String)
        fun onShowCategoryProducts(position: Int, categoryId: String)

    }

    override fun getItemCount(): Int {
        return allCategoryResponse.data.size
    }

    override fun getItemViewType(position: Int): Int {
        return masterCategoryModel.get(position).type
    }





    class CategoryViewHolder(var categoryItemBinding: CategoryItemBinding):RecyclerView.ViewHolder(categoryItemBinding.root)

    class SubCategoryViewHolder(var subcategoryBinding: SubcategoryItemBinding) : RecyclerView.ViewHolder(subcategoryBinding.root) {
    }



    // Need another viewholder for showing superCategories



}