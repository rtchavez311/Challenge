package rtchavez.challenge

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import rtchavez.challenge.data.api.ChallengeService
import rtchavez.challenge.di.repositoriesModule
import rtchavez.challenge.ui.ChallengesViewModel

class ChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChallengeApp)
            androidLogger(level = Level.DEBUG)
            modules(modules, repositoriesModule)
        }
    }

    val modules = module {
        single { ChallengeService.api }
        viewModel { ChallengesViewModel(get()) }
    }
}