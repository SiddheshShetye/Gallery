package com.siddroid.gallery.core

import android.app.Application
import com.squareup.picasso.Picasso
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GalleryApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val p: Picasso = Picasso.Builder(this)
            .build()
        p.setIndicatorsEnabled(false)
        p.isLoggingEnabled = true
        try {
            Picasso.setSingletonInstance(p)
        } catch (e: Exception) {
            //Instance already exists
        }

    }
}