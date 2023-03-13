package com.ushatech.aestoreskotlin.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.CartUserResponse
import com.ushatech.aestoreskotlin.data.room.AppDatabase
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.FragmentCartBinding
import com.ushatech.aestoreskotlin.presentation.CartViewModel
import com.ushatech.aestoreskotlin.session.AppSession
import com.ushatech.aestoreskotlin.session.Constant
import com.ushatech.aestoreskotlin.ui.adapter.CartAdapter
import com.ushatech.aestoreskotlin.ui.adapter.RoomCartAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : BaseFragment(),RoomCartAdapter.onRoomCartEvent,CartAdapter.onApiCartEvent {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentCartBinding

    private lateinit var viewModel:CartViewModel
    override fun onDeleteItemApi(cartTable: CartUserResponse.Items, quantity: Int) {
        val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
        val alertDialog = AlertDialog.Builder(fragmentContext)
        alertDialog.setTitle("Delete cart item ?")
        alertDialog.setCancelable(true)
        alertDialog.setPositiveButton("Yes",{dialog, which ->
            run {
                viewModel.deleteCartItem(userId.toString(),cartTable.cartId.toString())
            }
        })
        alertDialog.setNegativeButton("No",null)
        alertDialog.create()

        alertDialog.show()


    }

    override fun onUpdateItemApi(cartTable: CartUserResponse.Items, quantity: Int) {
        showToast("To be implemented 500 from backend")

    }

    override fun onDeleteItem(cartTable: UserCartTable, position: Int) {
        // show alert dialog before delete cart item
        showDeleteDialog(cartTable,position)


    }

    private fun showDeleteDialog(cartTable: UserCartTable, position: Int) {
        val alertDialog = AlertDialog.Builder(fragmentContext)
        alertDialog.setTitle("Delete cart item ?")
        alertDialog.setCancelable(true)
        alertDialog.setPositiveButton("Yes",{dialog, which ->
            run {

                deleteCartItemRoom(cartTable)


            }
        })
        alertDialog.setNegativeButton("No",null)
        alertDialog.create()

        alertDialog.show()

    }

    private fun deleteCartItemRoom(cartTable: UserCartTable) {
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                fragmentContext, AppDatabase::class.java, "aestores_online.db"
            ).build()
            db.userCartDao().deleteCartItem(cartTable)
            //update items for room adapter after delete.
            updateRoomRecycler()

        }

    }

    override fun onUpdateItem(cartTable: UserCartTable, position: Int) {
        updateCartItemRoom(cartTable)

    }

    private fun updateCartItemRoom(cartTable: UserCartTable) {
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                fragmentContext, AppDatabase::class.java, "aestores_online.db"
            ).build()
            db.userCartDao().updateCartItem(cartTable)
            //update items for room adapter after delete.
            updateRoomRecycler()
        }

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

        binding = FragmentCartBinding.inflate(LayoutInflater.from(fragmentContext),container,false)
        initClicks()
        setupViewModel()
        attachObservers()
        checkRecyclerAdapter()
        if(fragmentManager?.backStackEntryCount==3){
            binding.btnContinueShopping.visibility=View.VISIBLE
        }else{
            binding.btnContinueShopping.visibility=View.GONE
        }
        // Inflate the layout for this fragment
        return binding.root

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
        viewModel.userCartUserResponse.observe(viewLifecycleOwner){
            hideLoader()
            if(it!=null){
                updateRemoteRecycler(it)
            }
        }
        viewModel.deleteItemResponse.observe((viewLifecycleOwner)){
            hideLoader()
            if(it.status==1){
                val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
                viewModel.getUserCart(userId.toString())
            }
        }
        viewModel.deleteCompleteItemResponse.observe(viewLifecycleOwner){
            hideLoader()
            if(it.status==1){
                val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
                viewModel.getUserCart(userId.toString())
            }



        }

    }

    private fun setupViewModel() {

        viewModel = CartViewModel()
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding.viewmodel = viewModel





    }

    private fun checkRecyclerAdapter() {
        if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){
            val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
            showLoader()
            viewModel.getUserCart(userId.toString())
            binding.layoutItemPrice.visibility = View.VISIBLE
        }else{
            updateRoomRecycler()
            binding.layoutItemPrice.visibility = View.GONE

        }
    }

    private fun updateRemoteRecycler(cartUserResponse: CartUserResponse) {

        if(cartUserResponse.items.isEmpty()){
            showNoItemsPresent()
        }else{
            binding.recvCart.adapter = CartAdapter(fragmentContext,cartUserResponse.items,this)
            binding.recvCart.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.VERTICAL,false)
            binding.tvWholeTotal.text = "Rs ${cartUserResponse.total.toString()}"
            binding.tvShippingCharge.text = "Rs ${cartUserResponse.totalShipping.toString()}"
            binding.tvTaxPrice.text = "Rs ${cartUserResponse.totalTax.toString()}"
            binding.tvCartTotal.text = "Rs ${cartUserResponse.totalPrice.toString()}"
        }
    }

    private fun updateRoomRecycler() {

        GlobalScope.launch {
            val db = Room.databaseBuilder(
                fragmentContext, AppDatabase::class.java, "aestores_online.db"
            ).build()
            // Make the cart Object and store in Room Db

            val cartDataLocal =   db.userCartDao().getUserCartLocal()


            launch (Dispatchers.Main){
                if(cartDataLocal.isEmpty()){
                  showNoItemsPresent()

                }else{
                    binding.recvCart.adapter = RoomCartAdapter(fragmentContext,
                        cartDataLocal as ArrayList<UserCartTable>
                        ,this@CartFragment)
                    binding.recvCart.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.VERTICAL,false)

                }



            }



        }
        // We also need to set the cartCalculation layout.



    }

    private fun showNoItemsPresent() {
        binding.cartMainLayout.visibility = View.GONE
        binding.tvNoCartItem.visibility = View.VISIBLE
        binding.recvCart.visibility = View.GONE


    }

    private fun initClicks() {
        binding.btnCheckoutProceed.setOnClickListener {

            FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,BillingFragment(),BillingFragment::class.java.canonicalName,true)


        }

        binding.btnClearAll.setOnClickListener {


            if(AppSession(fragmentContext).getBoolean(Constant.IS_LOGGED_IN)){
                val userId = AppSession(fragmentContext).getString(Constant.USER_ID)
                showLoader()
                viewModel.deleteEntireCart(userId.toString())

            }else{
                val alertDialog = AlertDialog.Builder(fragmentContext)
                alertDialog.setTitle("Delete All cart items ?")
                alertDialog.setCancelable(true)
                alertDialog.setPositiveButton("Yes",{dialog, which ->
                    run {

                        deleteUserCartRoom()


                    }
                })
                alertDialog.setNegativeButton("No",null)
                alertDialog.create()

                alertDialog.show()
            }






        }
        binding.btnContinueShopping.setOnClickListener {

            // Need to implement logic for returning back
            if(fragmentManager?.backStackEntryCount==3){
                fragmentManager?.popBackStack()
                fragmentManager?.popBackStack()

            }

        }


    }

    private fun deleteUserCartRoom() {
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                fragmentContext, AppDatabase::class.java, "aestores_online.db"
            ).build()
            // Make the cart Object and store in Room Db


            db.userCartDao().deletUserCart()
            val cartDataLocal = db.userCartDao().getUserCartLocal()


            launch (Dispatchers.Main){
                showNoItemsPresent()

                binding.recvCart.adapter = RoomCartAdapter(fragmentContext,
                     cartDataLocal as ArrayList<UserCartTable>
                    ,this@CartFragment)
                binding.recvCart.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.VERTICAL,false)


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
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}