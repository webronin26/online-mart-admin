package com.webronin_26.online_mart_admin.add_retailer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart_admin.*
import com.webronin_26.online_mart_admin.data.source.TokenManager
import com.webronin_26.online_mart_admin.databinding.AddRetailerFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRetailerFragment : Fragment() {

    private lateinit var viewModel: AddRetailerViewModel
    private lateinit var viewDataBinding: AddRetailerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.add_retailer_fragment, container, false)

        viewModel = ViewModelProvider(this)[AddRetailerViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = AddRetailerFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        viewDataBinding.addRetailerFragmentCancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_add_retailer_fragment_to_main_fragment)
        }

        viewDataBinding.addRetailerFragmentSaveButton.setOnClickListener {

            if (viewModel.addRetailerName.get().isNullOrEmpty()) {
                Toast.makeText(context, "公司名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerResponsiblePerson.get().isNullOrEmpty()) {
                Toast.makeText(context, "負責人名稱不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerInvoice.get().isNullOrEmpty()) {
                Toast.makeText(context, "匯款帳號不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerRemittanceAccount.get().isNullOrEmpty()) {
                Toast.makeText(context, "匯款帳號不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerOfficePhone.get().isNullOrEmpty()) {
                Toast.makeText(context, "公司電話不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerPersonalPhone.get().isNullOrEmpty()) {
                Toast.makeText(context, "聯絡人電話不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerOfficeAddress.get().isNullOrEmpty()) {
                Toast.makeText(context, "公司地址不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerCorrespondenceAddress.get().isNullOrEmpty()) {
                Toast.makeText(context, "通訊地址不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.addRetailerDeliveryFee.get().isNullOrEmpty()) {
                Toast.makeText(context, "運費不得為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                viewModel.addRetailerDeliveryFee.get().toString().toFloat()
            } catch (e: Exception) {
                Toast.makeText(context, "運費請輸入 數字 類型", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val token = TokenManager.getToken(requireContext())

            if (!token.isNullOrEmpty()) {
                viewModel.addRetailer(token)
            } else {
                Toast.makeText(requireContext(), "尚未能更新", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "增加商品成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }
}