package com.example.aviamirror.presentation.main.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviamirror.repository.MainRepository
import com.example.aviamirror.repository.responses.TakeoffResponse
import com.example.aviamirror.utils.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel : ViewModel() {

    private val mainRepository = MainRepository()

    val popularTakeoffs: MutableLiveData<Result<List<TakeoffResponse>>> by lazy {
        MutableLiveData(Result())
    }

    val cities: MutableLiveData<Result<List<String>>> by lazy {
        MutableLiveData(Result())
    }

    val filterQueryResponse: MutableLiveData<Result<List<TakeoffResponse>>> by lazy {
        MutableLiveData(Result(data = null))
    }

    fun retrieveMostPopularTakeoffs() {
        mainRepository.getPopularTakeoffs().onEach { result ->
            when {
                result.isLoading -> {
                    popularTakeoffs.value = Result(isLoading = true)
                }
                result.isError -> {
                    popularTakeoffs.value = Result(isError = true)
                }
                else -> {
                    popularTakeoffs.value = Result(
                        data = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun retrieveCitiesList() {
        mainRepository.getCitiesList().onEach { result ->
            when {
                result.isLoading -> {
                    cities.value = Result(isLoading = true)
                }
                result.isError -> {
                    cities.value = Result(isError = true)
                }
                else -> {
                    cities.value = Result(
                        data = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

     fun retrieveFlightsByFilter(filter: TakeoffResponse) {
        mainRepository.findByFilterQuery(filter).onEach { result ->
            when {
                result.isLoading -> {
                    filterQueryResponse.value = Result(isLoading = true)
                }
                result.isError -> {
                    filterQueryResponse.value = Result(isError = true)
                }
                else -> {
                    filterQueryResponse.value = Result(
                        data = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}