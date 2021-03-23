package rtchavez.challenge.di

import org.koin.dsl.module
import rtchavez.challenge.data.api.ChallengeApi
import rtchavez.challenge.data.api.ChallengeService
import rtchavez.challenge.data.model.Data
import rtchavez.challenge.data.model.Upcoming
import rtchavez.challenge.data.repository.ChallengeRepository

val repositoriesModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
}

private class ChallengeRepositoryImpl(private val api: ChallengeApi) : ChallengeRepository {

    override suspend fun getUpcomingData(): Data<Upcoming> {
        val url = ChallengeService.upcomingDataUrl
        return api.getUpcomingData(url)
    }

}
