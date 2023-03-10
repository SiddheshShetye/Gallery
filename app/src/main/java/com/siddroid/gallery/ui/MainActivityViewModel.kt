package com.siddroid.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddroid.gallery.ui.model.GridViewState
import com.siddroid.gallery.ui.model.PhotoDetailsViewState
import com.siddroid.gallery.core.ConnectivityStatus
import com.siddroid.gallery.data.GalleryRepository
import com.siddroid.gallery.data.ImageModel
import com.siddroid.gallery.core.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val galleryRepository: GalleryRepository,
                                                private val mapper: StateMapper,
                                                connectivityStatus: ConnectivityStatus
): ViewModel() {

    private val _gridDataFlow = MutableStateFlow(GridViewState(listOf()))
    val gridDataFlow: StateFlow<GridViewState>
    get() = _gridDataFlow.asStateFlow()

    private var galleryData: List<ImageModel>? = null

    private val _detailsDataStateFlow = MutableStateFlow(PhotoDetailsViewState(listOf()))
    val detailsDataStateFlow: StateFlow<PhotoDetailsViewState>
    get() = _detailsDataStateFlow.asStateFlow()

    val state = connectivityStatus.networkStatus
        .distinctUntilChanged()
        .map(
        onUnavailable = {
            return@map false
                        },
        onAvailable = {
            return@map true
        }
    )

    fun getImages() {
        viewModelScope.launch {
            galleryData = galleryRepository.getImageList()
            galleryData?.let { galleryDataList -> _gridDataFlow.update { mapper.mapImageModelToGridViewState(galleryDataList) }  }
        }
    }

    fun getDetails() {
        viewModelScope.launch {
            galleryData?.let { galleryDataList ->
                _detailsDataStateFlow.update { mapper.mapImageModelToDetailsViewState(galleryDataList) }
            }
        }
    }

}