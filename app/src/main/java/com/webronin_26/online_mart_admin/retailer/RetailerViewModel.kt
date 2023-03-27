package com.webronin_26.online_mart_admin.retailer

import androidx.lifecycle.*
import com.webronin_26.online_mart_admin.Event
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_admin.data.source.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.webronin_26.online_mart_admin.data.remote.Result
import javax.inject.Inject

@HiltViewModel
class RetailerViewModel  @Inject constructor(private val adminRepository : AdminRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val retailerName = MutableLiveData<String>()
    val retailerResponsiblePerson = MutableLiveData<String>()
    val retailerInvoice = MutableLiveData<String>()
    val retailerRemittanceAccount = MutableLiveData<String>()
    val retailerOfficePhone = MutableLiveData<String>()
    val retailerPersonalPhone = MutableLiveData<String>()
    val retailerOfficeAddress = MutableLiveData<String>()
    val retailerCorrespondenceAddress = MutableLiveData<String>()
    val retailerDeliveryFee = MutableLiveData<Float>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // do nothing
    }

    fun refresh(token: String, retailerId: Int) {
        viewModelScope.launch {
            adminRepository.retailerQuery(token, retailerId).let { result ->
                when (result) {
                    is Result.Success -> {
                        retailerName.value = result.data.data.name
                        retailerResponsiblePerson.value = result.data.data.responsiblePerson
                        retailerInvoice.value = result.data.data.invoice
                        retailerRemittanceAccount.value = result.data.data.remittanceAccount
                        retailerOfficePhone.value = result.data.data.officePhone
                        retailerPersonalPhone.value = result.data.data.personalPhone
                        retailerOfficeAddress.value = result.data.data.officeAddress
                        retailerCorrespondenceAddress.value = result.data.data.correspondenceAddress
                        retailerDeliveryFee.value = result.data.data.deliveryFee

                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
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