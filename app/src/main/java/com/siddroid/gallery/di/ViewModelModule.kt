package com.siddroid.gallery.di

import android.content.Context
import com.siddroid.gallery.MainActivity
import com.siddroid.gallery.OnPhotoClickListener
import com.siddroid.gallery.PhotoGridAdapter
import com.siddroid.gallery.PhotoGridFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class ViewModelModule {
    @Provides
    @FragmentScoped
    fun providePhotoGridAdapter(onPhotoClickListener: OnPhotoClickListener): PhotoGridAdapter {
        return PhotoGridAdapter(onPhotoClickListener)
    }

    @Provides
    @FragmentScoped
    fun provideOnPhotoClickListener(@ActivityContext context: Context) : OnPhotoClickListener {
        return (context as MainActivity).supportFragmentManager.findFragmentByTag("photo_grid") as PhotoGridFragment
    }
}