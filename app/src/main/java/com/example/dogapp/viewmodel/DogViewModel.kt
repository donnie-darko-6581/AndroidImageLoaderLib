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

    private val _dogImageFlow = MutableStateFlow<String?>(null)
    val currentDog: StateFlow<String?> = _dogImageFlow

    private val _prevFlow = MutableStateFlow<Boolean>(false)
    val prevFlow: StateFlow<Boolean> = _prevFlow

    private val _nextFlow = MutableStateFlow<Boolean>(true)
    val nextFlow: StateFlow<Boolean> = _nextFlow

    init {
        fetchFirstDog()
    }

    // TODO made public for testing, there are better ways to handle
    fun fetchFirstDog() {
        viewModelScope.launch {
            val imageUrl = dogImageLib.getImage()
            _dogImageFlow.value = imageUrl
            _prevFlow.value = dogImageLib.hasPrevious()
            _nextFlow.value = dogImageLib.hasNext()
        }
    }

    fun loadNextDog() {
        viewModelScope.launch {
            val imageUrl = dogImageLib.getNextImage()
            _dogImageFlow.value = imageUrl
            _prevFlow.value = dogImageLib.hasPrevious()
            _nextFlow.value = dogImageLib.hasNext()
        }
    }

    fun loadPreviousDog() {
        viewModelScope.launch {
            val imageUrl = dogImageLib.getPreviousImage()
            _dogImageFlow.value = imageUrl
            _prevFlow.value = dogImageLib.hasPrevious()
            _nextFlow.value = dogImageLib.hasNext()
        }
    }

    fun fetchAndSaveMultipleDogs(count: String) {
        viewModelScope.launch {
            // note: we can call toInt() safely as kb allows only nums
            dogImageLib.getImages(count = count.toInt())
        }
    }
}