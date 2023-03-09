package com.ushatech.aestoreskotlin.ui.fragments

import android.app.Dialog
import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.ProductDetailResponseData
import com.ushatech.aestoreskotlin.data.room.AppDatabase
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.FragmentProductDetailBinding
import com.ushatech.aestoreskotlin.databinding.ImageDialogBinding
import com.ushatech.aestoreskotlin.presentation.ProductDetailViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant
import com.ushatech.aestoreskotlin.ui.adapter.ProductImageAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductDetailFragment : BaseFragment(), ProductImageAdapter.onEvent {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentProductDetailBinding

    private lateinit var viewModel:ProductDetailViewModel

    private lateinit var productLocal: ProductDetailResponseData


    override fun onImageClick(position: Int, drawable: String) {
        Glide.with(fragmentContext).load(drawable).placeholder(R.drawable.ic_banner_home).into(binding.ivMainProductImage)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        initClicks()

        setupViewModel()
        attachObservers()
        initialApiCall()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initialApiCall() {
        val userId = AppSession(fragmentContext).getUser()?.data?.id
        if(param1!=null){
            viewModel.getProductDetail(param1!!,userId.toString())
        }else{
            showToast("Showing static screen.")
        }
    }

    private fun attachObservers() {
        viewModel.isFailed.observe((viewLifecycleOwner)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        viewModel.isSuccess.observe((viewLifecycleOwner)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        viewModel.productResponse.observe((viewLifecycleOwner)){
            if(it!=null){
                updateUi(it)

            }else{
                showToast(Constant.OOPS_SW)
            }


        }
        viewModel.commonResponseData.observe((viewLifecycleOwner)){
            hideLoader()
            if(it.status==1){
              showToastLong(it.message.toString())

            }else{
                showToastLong(it.message.toString())
            }




        }

    }

    private fun updateUi(product: ProductDetailResponseData) {

        if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){
            // Call the addtoCartApi.
            showToast("Will Call AddToCart API.")
        }else{
            showToast("Using local DB.")
            makeLocalCartItem(product)
        }
        productLocal = product

        binding.tvProductName.text = product.data?.name
        binding.tvMaxPrice.text = "Rs ${product.data?.mrp}"
        binding.tvRealPrice.text = "Rs ${product.data?.price}"
        binding.tvStock.text = "${product.data?.stock} are available."
        binding.tvMaxPrice.setPaintFlags(binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        Glide.with(fragmentContext).load(product.data?.image).placeholder(R.drawable.ic_banner_home).into(binding.ivMainProductImage)

        if(product.data!!.moreImages.isEmpty()){

            binding.recvProductImages.visibility = View.GONE

        }else{
            val productCover = ProductDetailResponseData.MoreImages(product.data?.image)
            val productImagesModified = arrayListOf<ProductDetailResponseData.MoreImages>()
            productImagesModified.add(productCover)
            productImagesModified.addAll(product.data!!.moreImages)

            binding.recvProductImages.adapter = ProductImageAdapter(fragmentContext,productImagesModified,this)
            binding.recvProductImages.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.HORIZONTAL,false)
            binding.recvProductImages.visibility = View.VISIBLE
        }




    }

    private fun makeLocalCartItem(product: ProductDetailResponseData) :UserCartTable{
        val priceTotal = (product.data?.price?.toInt()
            ?.times(binding.tvQuantity.text.toString().toInt())).toString()
        val userCartTable = UserCartTable(productId = product.data?.id.toString(), priceProduct = product.data?.price.toString(), quantity = binding.tvQuantity.text.toString(), imageUrl =  product.data?.image.toString(), priceTotal = priceTotal, productName = product.data?.name.toString(), userId = "-1")

        userCartTable.productId = product.data?.id.toString()
        userCartTable.priceProduct = product.data?.price.toString()
        userCartTable.quantity =binding.tvQuantity.text.toString()
        userCartTable.imageUrl = product.data?.image.toString()

        // Could Cause issues if values received from backend is null

        userCartTable.productName = product.data?.name.toString()

        if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){
            userCartTable.userId = AppSession(fragmentContext).getUser()?.data?.id.toString()
        }else{
            // for local userId is always -1
            userCartTable.userId = "-1"

        }
        return userCartTable

    }

    private fun setupViewModel() {
        viewModel = ProductDetailViewModel()
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        binding.viewmodel = viewModel

    }

    private fun initClicks() {
        binding.addToCartLayout.setOnClickListener {

            // Call api if logged in else add to local db
            if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){



                FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),
                    com.ushatech.aestoreskotlin.R.id.activity_main_nav_host_fragment,CartFragment.newInstance("local","false"),CartFragment::class.java.canonicalName,true)


            }else{
                addItemToRoomDatabase(makeLocalCartItem(productLocal))

            }





        }
        binding.ivIncre.setOnClickListener {
            val currentQuantity = binding.tvQuantity.text.toString().toInt()
            if(currentQuantity>=1){
                binding.tvQuantity.text = (currentQuantity+1).toString()
                makeLocalCartItem(productLocal)
            }

        }
        binding.ivDecre.setOnClickListener {
            val currentQuantity =  binding.tvQuantity.text.toString().toInt()
            if(currentQuantity>1){
                binding.tvQuantity.text = (currentQuantity-1).toString()
                makeLocalCartItem(productLocal)

            }

        }


        binding.ivMainProductImage.setOnClickListener {
            val dialogBinding = ImageDialogBinding.inflate(LayoutInflater.from(fragmentContext))
            val dialog = Dialog(fragmentContext)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.imageDilag.setImageDrawable(binding.ivMainProductImage.drawable)
            dialog.show()

        }

        binding.btnCheckPincode.setOnClickListener {
            if(binding.edtPincode.text.toString().isEmpty()){
                binding.edtPincode.setError("Please enter pincode.")

            }else{
                showLoader()
                viewModel.checkPincode(param1.toString(),binding.edtPincode.text.toString())

            }


        }

        val content = SpannableString("DESCRIPTION")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.tvTitleDescription.text = content

    }



    private fun addItemToRoomDatabase(userCartTable: UserCartTable) {
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                fragmentContext, AppDatabase::class.java, "aestores_online.db"
            ).build()
            // Make the cart Object and store in Room Db

            // Add check if product already added to local then dont add.
            var isAlreadyAdded = false
            if(db.userCartDao().getUserCartLocal().isEmpty()){
                db.userCartDao().addProductToLocal(userCartTable)

            }else{
                for(cart in db.userCartDao().getUserCartLocal()){
                    if(cart.productId==productLocal.data?.id){
                        // Don't add the product again.
                        isAlreadyAdded = true
                        break
                    }else{
                        db.userCartDao().addProductToLocal(userCartTable)

                        break
                    }

                }

            }






            launch (Dispatchers.Main){

                if(isAlreadyAdded){
                    showToast("Already added to cart")
                }else{
                    showToast("Added to cart successfully ! [LOCAL]")

                }

                FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),
                    com.ushatech.aestoreskotlin.R.id.activity_main_nav_host_fragment,CartFragment.newInstance("local","true"),CartFragment::class.java.canonicalName,true)
                }



        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}