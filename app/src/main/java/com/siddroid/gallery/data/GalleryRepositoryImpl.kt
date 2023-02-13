package com.siddroid.gallery.data

import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(private val galleryService: GalleryService): GalleryRepository {
    override suspend fun getImageList(): List<ImageModel> {
        return galleryService.getImageList();
    }
}