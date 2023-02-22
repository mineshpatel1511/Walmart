package com.example.walmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmart.data.Country
import com.example.walmart.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CountryViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun getCountryList() {
        viewModelScope.launch {
            CountryRepository.getCountryList().asResult()
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is Result.Loading -> UiState.Loading
                            is Result.Success -> UiState.Success(result.data)
                            is Result.Error -> UiState.Error(result.exception)
                        }
                    }
                }
        }
    }

    sealed interface UiState {
        object Loading : UiState

        data class Success(
            val data: List<Country>
        ) : UiState

        data class Error(
            val throwable: Throwable? = null
        ) : UiState
    }

    sealed interface Result<out T> {
        data class Success<T>(val data: T) : Result<T>
        data class Error(val exception: Throwable? = null) : Result<Nothing>
        object Loading : Result<Nothing>
    }

    private fun <T> Flow<T>.asResult(): Flow<Result<T>> {
        return this
            .map<T, Result<T>> {
                Result.Success(it)
            }
            .onStart { emit(Result.Loading) }
            .catch { emit(Result.Error(it)) }
    }
}
