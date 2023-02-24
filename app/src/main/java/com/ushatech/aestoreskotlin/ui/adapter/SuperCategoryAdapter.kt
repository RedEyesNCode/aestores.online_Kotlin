package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.data.SubCateogryResponse
import com.ushatech.aestoreskotlin.databinding.SuperCategoryItemBinding

class SuperCategoryAdapter(var context: Context,var superCategories:ArrayList<SubCateogryResponse.Data>):RecyclerView.Adapter<SuperCategoryAdapter.SuperCategoryViewHolder>() {


    lateinit var binding: SuperCategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperCategoryViewHolder {
        binding = SuperCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return SuperCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuperCategoryViewHolder, position: Int) {
        holder.binding.tvSuperCategoryName.text = superCategories.get(position).name

    }

    override fun getItemCount(): Int {
        return superCategories.size
    }

    class SuperCategoryViewHolder(var binding:SuperCategoryItemBinding) :RecyclerView.ViewHolder(binding.root)
}