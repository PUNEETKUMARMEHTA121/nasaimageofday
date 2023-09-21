package com.example.nasaimageofday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaimageofday.repository.ImageRepository

class MainViewModelFactory(private val imageRepository: ImageRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(imageRepository) as T
    }
}
