package com.example.aviamirror.presentation.main.ticketscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviamirror.repository.MainRepository
import com.example.aviamirror.repository.responses.BuyTicketResponse
import com.example.aviamirror.repository.responses.Order
import com.example.aviamirror.utils.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BuyTicketViewModel : ViewModel() {

    private val mainRepository = MainRepository()

    val ticketData: MutableLiveData<Result<BuyTicketResponse>> by lazy {
        MutableLiveData(Result())
    }

    fun getTicketData(timetableId: Int) {
        mainRepository.getTicketData(timetableId).onEach { result ->
            when {
                result.isLoading -> {
                    ticketData.value = Result(isLoading = true)
                }
                result.isError -> {
                    ticketData.value = Result(isError = true)
                }
                else -> {
                    ticketData.value = Result(
                        data = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addOrderToHistory(order: Order) {
        mainRepository.addOrderToHistory(order)
    }
}