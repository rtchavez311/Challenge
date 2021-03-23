package rtchavez.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Venue(val city: String? = null, val state: String? = null)

@Serializable
data class Upcoming(
    val url: String,
    val startDate: String,
    val endDate: String,
    val name: String,
    val icon: String,
    val venue: Venue,
    val objType: String,
    val loginRequired: Boolean
)

@Serializable
data class Data<T>(val data: List<T>, val total: Int)