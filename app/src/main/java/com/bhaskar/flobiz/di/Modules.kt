package com.bhaskar.flobiz.di

import com.bhaskar.flobiz.prefs.LocalData
import com.bhaskar.flobiz.repo.ContentRepo
import com.bhaskar.flobiz.view_models.ContentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewModelModule = module {
    factory {
        ContentViewModel()
    }
}
val prefsModule = module {
    single {
        LocalData(androidContext())
    }
}

val repoModule = module {
    factory {
        ContentRepo(androidContext())
    }
}

