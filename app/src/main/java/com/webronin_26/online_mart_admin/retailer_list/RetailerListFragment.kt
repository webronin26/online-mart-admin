package com.webronin_26.online_mart_admin.retailer_list

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
import com.webronin_26.online_mart_admin.databinding.RetailerListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RetailerListFragment : Fragment() {

    private lateinit var viewModel: RetailerListViewModel
    private lateinit var viewDataBinding: RetailerListFragmentBinding

    private var retailerListAdapter: RetailerListAdapter? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val view = inflater.inflate(R.layout.retailer_list_fragment, container, false)

        viewModel = ViewModelProvider(this)[RetailerListViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewDataBinding = RetailerListFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        initAdapter()
        refreshRequest()
    }

    private fun initView() {

        viewModel.retailers.observe(this, EventObserver {
            (viewDataBinding.retailerListRecyclerView.adapter as RetailerListAdapter).submitList(it.toMutableList())
        })

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY ->
                    Toast.makeText(requireContext(), "目前沒有任何訂單", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.retailerId.observe(this, EventObserver {
            val bundle = Bundle()
            bundle.putInt("retailer_id", it)
            findNavController().navigate(R.id.action_retailer_list_fragment_to_retailer_fragment, bundle)
        })
    }

    private fun initAdapter() {

        val viewModel = viewDataBinding.viewmodel

        if (viewModel != null) {
            retailerListAdapter = RetailerListAdapter(viewModel)
            viewDataBinding.retailerListRecyclerView.adapter = retailerListAdapter
            (viewDataBinding.retailerListRecyclerView.adapter as RetailerListAdapter).submitList(null)
        }
    }

    private fun refreshRequest() {

        val token = TokenManager.getToken(requireContext())

        if (!token.isNullOrEmpty()) {
            viewModel.refreshList(token)
        } else {
            Toast.makeText(requireContext(), "目前沒有詳細資料", Toast.LENGTH_LONG).show()
        }
    }
}