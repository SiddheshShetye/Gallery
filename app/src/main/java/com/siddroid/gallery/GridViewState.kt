package com.siddroid.gallery

data class GridViewState(val imageUrlList: List<GridItem>)

data class GridItem(val url: String = "", val height: Int = 200, val width: Int = 100, val ratio: Float = 0.0F)