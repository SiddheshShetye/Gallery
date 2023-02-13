package com.siddroid.gallery

import com.siddroid.gallery.data.ImageModel

class StateMapper {

    fun mapImageModelTpGridViewState(imageModel: List<ImageModel>): GridViewState {
        val listOfUrl = mutableListOf<GridItem>()
        imageModel.forEach {
            listOfUrl.add(GridItem(it.url))
        }
        return GridViewState(listOfUrl)
    }
}