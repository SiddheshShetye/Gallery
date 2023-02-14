package com.siddroid.gallery.ui

import com.siddroid.gallery.ui.model.GridItem
import com.siddroid.gallery.ui.model.GridViewState
import com.siddroid.gallery.ui.model.PhotoDetailItem
import com.siddroid.gallery.ui.model.PhotoDetailsViewState
import com.siddroid.gallery.data.ImageModel

class StateMapper {

    fun mapImageModelToGridViewState(imageModel: List<ImageModel>): GridViewState {
        val listOfUrl = mutableListOf<GridItem>()
        imageModel.forEach {
            listOfUrl.add(GridItem(it.url))
        }
        return GridViewState(listOfUrl)
    }

    fun mapImageModelToDetailsViewState(imageModel: List<ImageModel>): PhotoDetailsViewState {
        val listOfDetails = mutableListOf<PhotoDetailItem>()
        imageModel.forEach {
            listOfDetails.add(PhotoDetailItem(it.hdurl, it.title, it.explanation))
        }
        return PhotoDetailsViewState(listOfDetails)
    }
}