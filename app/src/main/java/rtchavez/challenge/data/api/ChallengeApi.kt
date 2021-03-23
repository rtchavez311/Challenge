package rtchavez.challenge.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import rtchavez.challenge.data.model.Data
import rtchavez.challenge.data.model.Upcoming
import java.util.concurrent.TimeUnit

object ChallengeService {

    private const val TIMEOUT_SECONDS = 60L

    private val client: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .followRedirects(false)
            .addInterceptor(logger)
            .build()
    }

    val api: ChallengeApi by lazy {
        val contentType = MediaType.get("application/json")
        val converter = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }.asConverterFactory(contentType)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .client(client)
            .build()
            .create(ChallengeApi::class.java)
    }

    val baseUrl by lazy {
        val data = arrayOf<Byte>(104, 116, 116, 112, 115, 58, 47, 47, 103, 117, 105, 100, 101, 98, 111, 111, 107, 46, 99, 111, 109, 47).toByteArray()
        val charset = Charsets.UTF_8
        return@lazy data.toString(charset)
    }

    val upcomingDataUrl: String by lazy {
        val data = arrayOf<Byte>(115, 101, 114, 118, 105, 99, 101, 47, 118, 50, 47, 117, 112, 99, 111, 109, 105, 110, 103, 71, 117, 105, 100, 101, 115, 47).toByteArray()
        val charset = Charsets.UTF_8
        return@lazy "$baseUrl${data.toString(charset)}"
    }
}

interface ChallengeApi {

    @GET
    suspend fun getUpcomingData(@Url url: String): Data<Upcoming>

}