package com.ushatech.aestoreskotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.SelectionResponseData
import com.ushatech.aestoreskotlin.databinding.FragmentBillingBinding
import com.ushatech.aestoreskotlin.presentation.BillingViewModel
import com.ushatech.aestoreskotlin.ui.adapter.OrderItemAdapter
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BillingFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding:FragmentBillingBinding
    private lateinit var viewModel:BillingViewModel
    var countryId = -1
    var stateId = -1




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

        binding = FragmentBillingBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        setupViewModel()
        attachObservers()
        initialCall()

        binding.recvOrderItems.adapter = OrderItemAdapter(fragmentContext)
        binding.recvOrderItems.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.VERTICAL,false)

        initClicks()




        return binding.root
    }

    private fun initClicks() {

        binding.btnPay.setOnClickListener {
            showToast("Order placed !")
        }
        binding.menuState.setOnClickListener {
            if(countryId==-1){
                showToast("Please select country first.")
            }

        }
        binding.menuCity.setOnClickListener {
            if(stateId==-1){
                showToast("Please select city first.")
            }


        }
    }

    private fun initialCall() {
        showLoader()
        viewModel.getCountry()
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
        viewModel.countryResponse.observe((viewLifecycleOwner)){
            hideLoader()
            setupCountryDropDown(it.data)


        }
        viewModel.cityResponse.observe((viewLifecycleOwner)){
            hideLoader()
            setupCityDropDown(it.data)

        }
        viewModel.stateResponse.observe((viewLifecycleOwner)){
            hideLoader()
            setupStateDropDown(it.data)

        }



    }
    private fun setupCountryDropDown(data: ArrayList<SelectionResponseData.Data>) {
        val arrayData = ArrayList<String>()
        for (x in data){
            arrayData.add(x.name!!)
        }
        val array =arrayData.toTypedArray()
        val arrayTypeAdapter = ArrayAdapter(fragmentContext,R.layout.drop_down_item,array)
        binding.menuCountry.setAdapter(arrayTypeAdapter)
        binding.menuCountry.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            if(!binding.menuCountry.text.isEmpty()){
                binding.menuState.setText("")
                binding.menuCity.setText("")
                countryId = data.get(position).id!!.toInt()
                viewModel.getStates(countryId.toString())
            }else{
                countryId = data.get(position).id!!.toInt()
                viewModel.getStates(countryId.toString())
            }



        }

    }

    private fun setupStateDropDown(data: ArrayList<SelectionResponseData.Data>) {
        val arrayData = ArrayList<String>()
        for (x in data){
            arrayData.add(x.name!!)
        }
        val array =arrayData.toTypedArray()
        val arrayTypeAdapter = ArrayAdapter(fragmentContext,R.layout.drop_down_item,array)
        binding.menuState.setAdapter(arrayTypeAdapter)
        binding.menuState.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if(!binding.menuState.text.isEmpty()){
                binding.menuCity.setText("")
                stateId = data.get(position).id!!.toInt()
                viewModel.getCities(stateId.toString())
            }else{
                stateId = data.get(position).id!!.toInt()
                viewModel.getCities(stateId.toString())

            }


        }


    }

    private fun setupCityDropDown(data: ArrayList<SelectionResponseData.Data>) {
        val arrayData = ArrayList<String>()
        for (x in data){
            arrayData.add(x.name!!)
        }
        val array =arrayData.toTypedArray()
        val arrayTypeAdapter = ArrayAdapter(fragmentContext,R.layout.drop_down_item,array)
        binding.menuCity.setAdapter(arrayTypeAdapter)



    }


    private fun setupViewModel() {
        viewModel = BillingViewModel()
        viewModel = ViewModelProvider(this).get(BillingViewModel::class.java)
        binding.viewmodel = viewModel

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BillingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BillingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}