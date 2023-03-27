package com.webronin_26.online_mart_admin.logout

import androidx.lifecycle.*
import com.webronin_26.online_mart_admin.Event
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_admin.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_admin.data.source.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.webronin_26.online_mart_admin.data.remote.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel  @Inject constructor(private val adminRepository : AdminRepository) : ViewModel(), LifecycleEventObserver {

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // do nothing
    }

    fun logout(token: String) {
        viewModelScope.launch {
            adminRepository.logout(token).let { result ->
                when (result) {
                    is Result.Success ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                }
            }
        }
    }
}