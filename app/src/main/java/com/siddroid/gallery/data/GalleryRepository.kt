package com.siddroid.gallery.data

interface GalleryRepository {
    suspend fun getImageList(): List<ImageModel>
}