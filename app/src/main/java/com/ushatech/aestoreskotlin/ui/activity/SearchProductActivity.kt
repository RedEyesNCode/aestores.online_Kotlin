package com.ushatech.aestoreskotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseActivity
import com.ushatech.aestoreskotlin.data.SearchProductResponse
import com.ushatech.aestoreskotlin.databinding.ActivitySearchProductBinding
import com.ushatech.aestoreskotlin.presentation.SearchProductViewModel
import com.ushatech.aestoreskotlin.ui.adapter.SearchProductAdapter
import com.ushatech.aestoreskotlin.ui.fragments.HomeFragment
import com.ushatech.aestoreskotlin.ui.fragments.ProductDetailFragment
import com.ushatech.aestoreskotlin.uitls.FragmentUtils


class SearchProductActivity : BaseActivity() ,SearchProductAdapter.onClickSearch{

    private lateinit var binding:ActivitySearchProductBinding

    private  var arrayListSearchProduct :ArrayList<SearchProductResponse.Products> = ArrayList()
    private lateinit var searchViewModel:SearchProductViewModel
    private lateinit var searchAdapter :SearchProductAdapter

    override fun onProductClick(position: Int, productId: String) {

        // Move to the Product detail fragment. but container is gone revert back to dashboard with data.
        val intent = Intent()
        intent.putExtra("PRODUCT_ID", productId)
        setResult(RESULT_OK, intent)
        finish()

        /*FragmentUtils().replaceFragmentBackStack(supportFragmentManager,R.id.activity_main_nav_host_fragment,
            ProductDetailFragment.newInstance(productId,""),
            ProductDetailFragment::class.java.canonicalName,false)*/


    }

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
                binding.tvSearchStatus.setTextColor(getColor(R.color.black))
                binding.searchStatusLayout.background = ContextCompat.getDrawable(this@SearchProductActivity,R.drawable.background_green_solid)
                binding.searchProgress.visibility = View.VISIBLE
                binding.tvSearchStatus.text = "Searching for products"
                binding.searchView.visibility = View.VISIBLE

                showLog(s.toString())

                Handler().postDelayed(Runnable {  searchViewModel.searchProduct(s.toString(),null,null,null)  },2000)



            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // Showloader will cause leaked window error
//                showLoader()



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {


            }
        })

    }

    private fun setupSearchRecycler() {
        searchAdapter =SearchProductAdapter(this,arrayListSearchProduct,this)
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
        searchViewModel.isLoading.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        searchViewModel.searchProductResponse.observe((this)){
            hideLoader()

            if(it.status==1){
                binding.searchStatusLayout.visibility = View.GONE

                updateSearchRecycler(it.data!!.products)
                binding.recvSearchProducts.visibility = View.VISIBLE
                binding.ivNotFound.visibility = View.GONE
            }else{
                binding.recvSearchProducts.visibility = View.GONE
                binding.ivNotFound.visibility = View.GONE

                binding.searchStatusLayout.visibility = View.VISIBLE
                binding.searchStatusLayout.background = ContextCompat.getDrawable(this@SearchProductActivity,
                    R.drawable.background_red_solid)
                binding.tvSearchStatus.setTextColor(getColor(R.color.white))
                binding.searchProgress.visibility = View.GONE
                binding.tvSearchStatus.text = "No Products found"




            }


        }
    }

    private fun updateSearchRecycler(products: ArrayList<SearchProductResponse.Products>) {
        arrayListSearchProduct = products
        searchAdapter = SearchProductAdapter(this@SearchProductActivity,arrayListSearchProduct,this)
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