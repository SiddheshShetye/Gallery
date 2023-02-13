package com.siddroid.gallery

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