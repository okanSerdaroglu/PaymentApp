package com.okan.paymentapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.okan.paymentapp.R
import com.okan.paymentapp.model.PaymentResult
import com.okan.paymentapp.util.DataState
import com.okan.paymentapp.viewmodel.MainStateEvent
import com.okan.paymentapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment
constructor(
) : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private val TAG: String = "AppDebug"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.setStateEvent(
            mainStateEvent = MainStateEvent.SendPayment
        )
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<PaymentResult> -> {
                    displayProgressBar(false)
                    Log.d(TAG, dataState.data.posID)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) {
            text.text = message
        } else {
            text.text = "Unknown Error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

}