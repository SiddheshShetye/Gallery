package com.siddroid.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddroid.gallery.data.GalleryRepository
import com.siddroid.gallery.data.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val galleryRepository: GalleryRepository, private val mapper: StateMapper): ViewModel() {

    private val _gridDataFlow = MutableStateFlow(GridViewState(listOf()))
    val gridDataFlow: StateFlow<GridViewState>
    get() = _gridDataFlow.asStateFlow()
    private var galleryData: List<ImageModel>? = null

    fun getImages() {
        viewModelScope.launch {
            galleryData = galleryRepository.getImageList()
            galleryData?.let { galleryDataList -> _gridDataFlow.update { mapper.mapImageModelTpGridViewState(galleryDataList) }  }
        }
    }

}