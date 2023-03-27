package com.webronin_26.online_mart_admin.retailer_list

import androidx.lifecycle.*
import com.webronin_26.online_mart_admin.*
import com.webronin_26.online_mart_admin.data.remote.Response
import com.webronin_26.online_mart_admin.data.remote.Result
import com.webronin_26.online_mart_admin.data.source.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetailerListViewModel  @Inject constructor(private val adminRepository : AdminRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()
    val retailers = MutableLiveData<Event<List<Response.Retailers>>>()
    val retailerId =  MutableLiveData<Event<Int>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshList(token: String) {
        viewModelScope.launch {
            adminRepository.retailerList(token).let { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data.count != 0) {
                            val retailerList =  result.data.data.toMutableList()
                            if (retailerList.size != 0) {
                                retailers.value = Event(retailerList)
                                viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                            } else {
                                viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY)
                            }
                        } else {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY)
                        }
                    }
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }
}