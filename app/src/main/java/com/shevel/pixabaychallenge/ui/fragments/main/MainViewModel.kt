package com.shevel.pixabaychallenge.ui.fragments.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevel.pixabaychallenge.model.ImageData
import com.shevel.pixabaychallenge.network.PixabayApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel() : ViewModel() {

    @Inject
    lateinit var api: PixabayApi

    var imagesLiveData = MutableLiveData<List<ImageData>>()
    var isLoadingLiveData = MutableLiveData(false)

    fun getNewImages(query: String) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            val images = api.getImages(query)
            imagesLiveData.postValue(images.hits)
            isLoadingLiveData.postValue(false)
        }
    }
}