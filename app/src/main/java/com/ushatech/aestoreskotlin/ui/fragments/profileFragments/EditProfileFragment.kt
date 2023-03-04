package com.ushatech.aestoreskotlin.ui.fragments.profileFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.SelectionResponseData
import com.ushatech.aestoreskotlin.databinding.FragmentEditProfileBinding
import com.ushatech.aestoreskotlin.presentation.EditProfileViewModel
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EditProfileFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding:FragmentEditProfileBinding
    lateinit var viewModel:EditProfileViewModel
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
        binding = FragmentEditProfileBinding.inflate(layoutInflater)

        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCall()
        return binding.root

    }

    private fun initialApiCall() {
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

    private fun setupStateDropDown(data: ArrayList<SelectionResponseData.Data>) {
        val arrayData = ArrayList<String>()
        for (x in data){
            arrayData.add(x.name!!)
        }
        val array =arrayData.toTypedArray()
        val arrayTypeAdapter = ArrayAdapter(fragmentContext,R.layout.drop_down_item,array)
        binding.menuState.setAdapter(arrayTypeAdapter)
        binding.menuState.onItemClickListener = AdapterView.OnItemClickListener { _, _, position,_ ->
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

    private fun setupCountryDropDown(data: ArrayList<SelectionResponseData.Data>) {
        val arrayData = ArrayList<String>()
        for (x in data){
            arrayData.add(x.name!!)
        }
        val array =arrayData.toTypedArray()
        val arrayTypeAdapter = ArrayAdapter(fragmentContext,R.layout.drop_down_item,array)
        binding.menuCountry.setAdapter(arrayTypeAdapter)
        binding.menuCountry.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

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

    private fun setupViewModel() {
        viewModel = EditProfileViewModel()
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        binding.viewmodel = viewModel




    }

    private fun initClicks() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}