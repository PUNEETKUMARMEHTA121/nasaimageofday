package com.example.nasaimageofday

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaimageofday.model.ApiImageResponse
import com.example.nasaimageofday.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.nasaimageofday.model.Result

class MainViewModel(
    val imageRepository: ImageRepository
) : ViewModel() {
    val imageModel: MutableLiveData<ApiImageResponse> = MutableLiveData()
    val errorResponse: MutableLiveData<Exception> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getImageOfDay() {
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = imageRepository.getImageOfDay()
            when (result) {
                is Result.Success -> {
                    val data = result.data // Access the successful data
                    // Handling the successful data
                    imageRepository.saveImage(data)
                    imageModel.postValue(data)
                }
                is Result.Error -> {
                    val error = result.exception // Access the error exception
                    // Handling the error data
                    errorResponse.postValue(error)
                }
            }
            loading.postValue(false)
        }
    }
}