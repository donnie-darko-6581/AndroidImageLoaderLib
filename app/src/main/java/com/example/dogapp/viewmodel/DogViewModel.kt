package com.example.dogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel: ViewModel() {

    private val dogImages = mutableListOf<String>()

    private val _dogImageFlow = MutableStateFlow<String?>(null)
    val dogImageFlow: StateFlow<String?> = _dogImageFlow

    init {
        fetchRandomDog()
    }

    fun fetchRandomDog() {
        viewModelScope.launch {
            val imageUrl = ""
            imageUrl.let {
                dogImages.add(it)
                _dogImageFlow.value = it
            }
        }
    }

    fun loadNextDog() {

    }

    fun loadPreviousDog() {
    }
}