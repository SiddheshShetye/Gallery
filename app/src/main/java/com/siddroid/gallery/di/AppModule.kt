package com.siddroid.gallery.di

import android.content.Context
import android.net.ConnectivityManager
import com.siddroid.gallery.BuildConfig
import com.siddroid.gallery.StateMapper
import com.siddroid.gallery.data.GalleryRepository
import com.siddroid.gallery.data.GalleryRepositoryImpl
import com.siddroid.gallery.data.GalleryService
import com.siddroid.gallery.data.ConnectivityStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideGalleryRepository(galleryService: GalleryService): GalleryRepository {
        return GalleryRepositoryImpl(galleryService)
    }

    @Provides
    @Singleton
    fun provideGalleryService(retrofit: Retrofit): GalleryService {
        return retrofit.create(GalleryService::class.java)
    }

    @Provides
    @Singleton
    fun provideStateMapper(): StateMapper {
        return StateMapper()
    }

    @Provides
    @Singleton
    fun provideNetworkTracker(connectivityManager: ConnectivityManager): ConnectivityStatus {
        return ConnectivityStatus(connectivityManager)
    }
}