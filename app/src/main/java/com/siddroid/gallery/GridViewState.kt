package com.siddroid.gallery

data class GridViewState(val imageUrlList: List<GridItem>)

data class GridItem(val url: String = "", val height: Int = 0, val width: Int = 0, val ratio: Float = 0.0F)