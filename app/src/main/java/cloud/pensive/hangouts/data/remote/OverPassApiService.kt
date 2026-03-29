package cloud.pensive.hangouts.data.remote

import cloud.pensive.hangouts.data.remote.models.OverpassResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface OverpassApiService {
    @GET("api/interpreter")
    suspend fun searchNearby(
        @Query("data") query: String
    ): OverpassResponse
}

object OverpassApi {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun create(): OverpassApiService =
        Retrofit.Builder()
            .baseUrl("https://overpass.private.coffee/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(OverpassApiService::class.java)
}