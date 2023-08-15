package com.example.dogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogimageloader.DogImageLib
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel(
    private val dogImageLib: DogImageLib = DogImageLib.getInstance()
): ViewModel() {

    private val dogImages = mutableListOf<String>()

    private val _dogImageFlow = MutableStateFlow<String?>(null)
    val currentDog: StateFlow<String?> = _dogImageFlow


    init {
        fetchFirstDog()
    }

    // TODO made public for testing, there are better ways to handle
    fun fetchFirstDog() {
        viewModelScope.launch {
            val imageUrl = dogImageLib.getImage()
            dogImages.add(imageUrl)
            _dogImageFlow.value = imageUrl
        }
    }

    fun loadNextDog() {
        viewModelScope.launch {
            val imageUrl = dogImageLib.getNextImage()
            dogImages.add(imageUrl)
            _dogImageFlow.value = imageUrl
        }
    }

    fun loadPreviousDog() {
        viewModelScope.launch {
            val imageUrl = dogImageLib.getPreviousImage()
            dogImages.add(imageUrl)
            _dogImageFlow.value = imageUrl
        }
    }

    fun fetchAndSaveMultipleDogs(count: String) {
        viewModelScope.launch {
            // note: we can call toInt() safely as kb allows only nums
            dogImageLib.getImages(count = count.toInt())
        }
    }
}