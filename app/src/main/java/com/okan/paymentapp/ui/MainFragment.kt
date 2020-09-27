package com.okan.paymentapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        sendPaymentEvent()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<PaymentResult> -> {
                    displayProgressBar(false)
                    displaySuccess()
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
            text_view_message.text = message
        } else {
            text_view_message.text = getString(R.string.unknown_error)
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun sendPaymentEvent() {
        button_pay.setOnClickListener {
            viewModel.setStateEvent(
                mainStateEvent = MainStateEvent.SendPayment
            )
        }
    }

    private fun displaySuccess() {
        image_view_success.visibility = View.VISIBLE
        text_view_message.text = getString(R.string.success_payment_message)
    }

}