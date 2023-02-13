package com.siddroid.gallery.data

import retrofit2.http.GET

interface GalleryService {

    @GET("take-home-exercise-data/trunk/nasa-pictures.json")
    suspend fun getImageList() : List<ImageModel>

}