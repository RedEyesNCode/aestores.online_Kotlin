package com.ushatech.aestoreskotlin.ui.fragments

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
import com.ushatech.aestoreskotlin.ui.adapter.SimilarProductAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductDetailFragment : BaseFragment(), ProductImageAdapter.onEvent,SimilarProductAdapter.onEventSimilarProduct {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentProductDetailBinding

    private lateinit var viewModel:ProductDetailViewModel

    private lateinit var productLocal: ProductDetailResponseData
    var isAvailable = false
    override fun onProductClick(position: Int, productId: String) {

        FragmentUtils().addFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,
            newInstance(productId,""),
            ProductDetailFragment::class.java.canonicalName,true)


    }

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
            hideLoader()
            if(it!=null){
                updateUi(it)

            }else{
                showToast(Constant.OOPS_SW)
            }


        }
        viewModel.commonResponseData.observe((viewLifecycleOwner)){
            hideLoader()
            if(it.status==1){
                isAvailable = true
              showToastLong(it.message.toString())

            }else{
                showToastLong(it.message.toString())
            }




        }
        viewModel.userCartResponse.observe((viewLifecycleOwner)){
            hideLoader()
            showToast(it.message.toString())
            FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),
                com.ushatech.aestoreskotlin.R.id.activity_main_nav_host_fragment,CartFragment.newInstance("local","false"),CartFragment::class.java.canonicalName,true)





        }

    }

    private fun updateUi(product: ProductDetailResponseData) {

        if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){
            // Call the addtoCartApi.
            showToast("Using Webservices.")
            val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
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


        setupRatingBars(product)
        setupYoutubeThumbnail(product.data?.youtubeurl.toString())
        if(product.data?.similarProducts!=null){
            if(!product.data?.similarProducts?.isEmpty()!!){
                setupSimilarProducts(product.data!!.similarProducts)

            }
        }




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

    private fun setupSimilarProducts(similarProducts: ArrayList<ProductDetailResponseData.SimilarProducts>) {
        binding.recvSimilarProduct.adapter = SimilarProductAdapter(fragmentContext,similarProducts,this)
        binding.recvSimilarProduct.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setupYoutubeThumbnail(videoId: String) {
        try {
            Glide.with(fragmentContext).load("${Constant.Youtube_image_Url}$videoId/default.jpg").placeholder(R.drawable.ic_banner_home).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.ytThumbnaillayout.visibility = View.GONE
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    showLog("Youtube Thumbnail is loaded.")
                    return true

                }
            }).into(binding.ivYoutubeThumbnail)

        }catch (e:HttpException){
            binding.ivYoutubeThumbnail.visibility = View.GONE
        }

        binding.ivYoutubeThumbnail.setOnClickListener {
            // open youtube intent
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$videoId")
            )
            try {
                fragmentContext.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                fragmentContext.startActivity(webIntent)
            }
        }
    }

    private fun setupRatingBars(product: ProductDetailResponseData) {

        if(product.data?.ratingCount!=0){
            binding.tvTotalRatings.text = "Avg. Star Rating (${product.data?.ratingCount.toString()} Rating)"
        }
        if(product.data?.avgRating!=null){
            binding.tvAvgRatingCount.text = "${product.data?.avgRating}"
        }

        binding.progressOneStar.tvStarNumber.text = "1 Star"
        binding.progressTwoStar.tvStarNumber.text = "2 Star"
        binding.progressThreeStar.tvStarNumber.text = "3 Star"
        binding.progressFourStar.tvStarNumber.text = "4 Star"
        binding.progressFiveStar.tvStarNumber.text = "5 Star"
        binding.progressOneStar.tvStarPercentage.text = "${product.data?.rating1PercentCount.toString()} Percentage"
        binding.progressTwoStar.tvStarPercentage.text = "${product.data?.rating2PercentCount.toString()} Percentage"
        binding.progressThreeStar.tvStarPercentage.text = "${product.data?.rating3percentCount.toString()} Percentage"
        binding.progressFourStar.tvStarPercentage.text = "${product.data?.rating4PercentCount.toString()} Percentage"
        binding.progressFiveStar.tvStarPercentage.text = "${product.data?.rating5PercentCount.toString()} Percentage"

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
            if(isAvailable){
                if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){

                    // Calling the api addToCart
                    showLoader()
                    val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
                    viewModel.addToCart(userId!!,productLocal.data?.id.toString(),binding.tvQuantity.text.toString())
                }else{
                    addItemToRoomDatabase(makeLocalCartItem(productLocal))

                }
            }else if(binding.edtPincode.text.toString().isEmpty()){
                showToast("Please check pincode to proceed.")
            }else if(!binding.edtPincode.text.toString().isEmpty()){
                showToast("Please check pincode to proceed.")
            }else{
                showToast("Not available in your area.")
            }

            // Call api if logged in else add to local db






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