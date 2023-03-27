package com.webronin_26.online_mart_admin.retailer

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
import com.webronin_26.online_mart_admin.databinding.RetailerFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RetailerFragment : Fragment() {

    private lateinit var viewModel: RetailerViewModel
    private lateinit var viewDataBinding: RetailerFragmentBinding

    private var retailerId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.retailer_fragment, container, false)

        viewModel = ViewModelProvider(this)[RetailerViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = RetailerFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        retailerId = arguments?.getInt("retailer_id") ?: 0

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        refreshRequest()
    }

    private fun initView() {

        viewDataBinding.retailerFragmentCancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_retailer_fragment_to_retailer_list_fragment)
        }

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun refreshRequest() {

        val token = TokenManager.getToken(requireContext())

        if (!token.isNullOrEmpty() && retailerId != 0) {
            viewModel.refresh(token, retailerId)
        } else {
            Toast.makeText(requireContext(), "目前沒有詳細資料", Toast.LENGTH_LONG).show()
        }
    }
}