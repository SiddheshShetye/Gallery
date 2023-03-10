package com.siddroid.gallery.di

import android.content.Context
import com.siddroid.gallery.ui.MainActivity
import com.siddroid.gallery.ui.OnPhotoClickListener
import com.siddroid.gallery.ui.adapters.PhotoGridAdapter
import com.siddroid.gallery.ui.fragments.PhotoGridFragment
import com.siddroid.gallery.ui.adapters.PhotoDetailsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
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

    @Provides
    @FragmentScoped
    fun providePhotoDetailsAdapter(): PhotoDetailsAdapter {
        return PhotoDetailsAdapter()
    }
}