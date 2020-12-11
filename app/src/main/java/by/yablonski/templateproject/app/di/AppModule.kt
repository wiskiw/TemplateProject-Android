package by.yablonski.templateproject.app.di

import by.yablonski.templateproject.app.App
import by.yablonski.templateproject.domain.repository.DemoRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object AppModule {

    val modules = module {

        single { DemoRepository(androidApplication() as App) }

    }

}
