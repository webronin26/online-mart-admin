package com.webronin_26.online_mart_admin.add_retailer

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.webronin_26.online_mart_admin.Event
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_admin.data.remote.Result
import com.webronin_26.online_mart_admin.data.source.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRetailerViewModel  @Inject constructor(private val adminRepository : AdminRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val addRetailerName = ObservableField<String>()
    val addRetailerResponsiblePerson = ObservableField<String>()
    val addRetailerInvoice = ObservableField<String>()
    val addRetailerRemittanceAccount = ObservableField<String>()
    val addRetailerOfficePhone = ObservableField<String>()
    val addRetailerPersonalPhone = ObservableField<String>()
    val addRetailerOfficeAddress = ObservableField<String>()
    val addRetailerCorrespondenceAddress = ObservableField<String>()
    val addRetailerDeliveryFee = ObservableField<String>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun addRetailer(token: String) {
        viewModelScope.launch {
            adminRepository.addRetailer(token,
                addRetailerName.get()!!,
                addRetailerResponsiblePerson.get()!!,
                addRetailerInvoice.get()!!,
                addRetailerRemittanceAccount.get()!!,
                addRetailerOfficePhone.get()!!,
                addRetailerPersonalPhone.get()!!,
                addRetailerOfficeAddress.get()!!,
                addRetailerCorrespondenceAddress.get()!!,
                addRetailerDeliveryFee.get()!!.toFloat()).let { result ->
                when (result) {
                    is Result.Success -> {
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                        setAllValueNull()
                    }
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }

    private fun setAllValueNull() {
        addRetailerName.set("")
        addRetailerResponsiblePerson.set("")
        addRetailerInvoice.set("")
        addRetailerRemittanceAccount.set("")
        addRetailerOfficePhone.set("")
        addRetailerPersonalPhone.set("")
        addRetailerOfficeAddress.set("")
        addRetailerCorrespondenceAddress.set("")
        addRetailerDeliveryFee.set("")
    }
}