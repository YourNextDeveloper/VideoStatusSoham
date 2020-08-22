package com.example.videostatus.factory


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.videostatus.viewmodels.*

class ViewModelProviderFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(application = application) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(application = application) as T
            modelClass.isAssignableFrom(AddVideoViewModel::class.java) -> AddVideoViewModel(application = application) as T
            modelClass.isAssignableFrom(FavouiteViewModel::class.java) -> FavouiteViewModel(application = application) as T
            modelClass.isAssignableFrom(DownloadViewModel::class.java) -> DownloadViewModel(application = application) as T
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(application = application) as T
            modelClass.isAssignableFrom(SelectLanguageViewModel::class.java) -> SelectLanguageViewModel(
                application = application
            ) as T
            modelClass.isAssignableFrom(VideoPlayerViewModel::class.java) -> VideoPlayerViewModel(
                application = application
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
