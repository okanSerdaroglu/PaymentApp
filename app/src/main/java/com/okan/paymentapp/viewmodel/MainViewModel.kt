package com.okan.paymentapp.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okan.paymentapp.model.PaymentResult
import com.okan.paymentapp.repository.MainRepository
import com.okan.paymentapp.util.DataState
import com.okan.paymentapp.viewmodel.MainStateEvent.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: MainRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<PaymentResult>> = MutableLiveData()

    val dataState: LiveData<DataState<PaymentResult>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is SendPayment -> {
                    mainRepository.sendPayment()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is None -> {}
            }
        }
    }
}

sealed class MainStateEvent {

    object SendPayment : MainStateEvent()

    object None : MainStateEvent()

}