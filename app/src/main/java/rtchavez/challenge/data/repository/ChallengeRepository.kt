package rtchavez.challenge.data.repository

import rtchavez.challenge.data.model.Data
import rtchavez.challenge.data.model.Upcoming

interface ChallengeRepository {
    suspend fun getUpcomingData(): Data<Upcoming>
}