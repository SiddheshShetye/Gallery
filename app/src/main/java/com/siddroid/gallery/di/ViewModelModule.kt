package com.siddroid.gallery.di

import android.content.Context
import com.siddroid.gallery.PhotoGridAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class ViewModelModule {
    @Provides
    @FragmentScoped
    fun providePhotoGridAdapter(@ApplicationContext context: Context): PhotoGridAdapter {
        return PhotoGridAdapter(context)
    }
}