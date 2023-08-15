package com.example.dogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogimageloader.DogImageLib
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel: ViewModel() {

    private val dogImages = mutableListOf<String>()

    private val _dogImageFlow = MutableStateFlow<String?>(null)
    val currentDog: StateFlow<String?> = _dogImageFlow

    private val dogImageLib = DogImageLib.getInstance()

    init {
        fetchRandomDog()
    }

    fun fetchRandomDog() {
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
}