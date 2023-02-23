package com.ushatech.aestoreskotlin.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.data.SearchProductResponse
import com.ushatech.aestoreskotlin.databinding.ActivitySearchProductBinding
import com.ushatech.aestoreskotlin.presentation.SearchProductViewModel
import com.ushatech.aestoreskotlin.ui.adapter.SearchProductAdapter


class SearchProductActivity : BaseActivity() {

    private lateinit var binding:ActivitySearchProductBinding

    private  var arrayListSearchProduct :ArrayList<SearchProductResponse.Products> = ArrayList()
    private lateinit var searchViewModel:SearchProductViewModel
    private lateinit var searchAdapter :SearchProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProductBinding.inflate(layoutInflater)
        binding.ivClose.setOnClickListener {
            finish()
        }

        setupViewModel()
        attachObservers()
        setupSearchRecycler()
        editTextListeners()
        setContentView(binding.root)
    }

    private fun editTextListeners() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {


            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                showLoader()
                showLog(s.toString())
                searchViewModel.searchProduct(s.toString(),null,null,null)
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {


            }
        })

    }

    private fun setupSearchRecycler() {
        searchAdapter =SearchProductAdapter(this,arrayListSearchProduct)
        binding.recvSearchProducts.adapter = searchAdapter
        binding.recvSearchProducts.layoutManager = GridLayoutManager(this@SearchProductActivity,2)


    }

    private fun attachObservers() {
        searchViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        searchViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        searchViewModel.searchProductResponse.observe((this)){
            hideLoader()

            if(it.status==1){
                updateSearchRecycler(it.data!!.products)
                binding.recvSearchProducts.visibility = View.VISIBLE
                binding.ivNotFound.visibility = View.GONE
            }else{
                binding.recvSearchProducts.visibility = View.GONE
                binding.ivNotFound.visibility = View.VISIBLE

            }


        }
    }

    private fun updateSearchRecycler(products: ArrayList<SearchProductResponse.Products>) {
        arrayListSearchProduct = products
        searchAdapter = SearchProductAdapter(this@SearchProductActivity,arrayListSearchProduct)
        binding.recvSearchProducts.adapter = searchAdapter
        binding.recvSearchProducts.layoutManager = GridLayoutManager(this@SearchProductActivity,2)


        searchAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        searchViewModel = SearchProductViewModel()
        searchViewModel = ViewModelProvider(this).get(SearchProductViewModel::class.java)
        binding.viewmodel = searchViewModel
    }
}