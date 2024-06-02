package ua.dp.klio.wiu.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.dp.klio.wiu.model.CastResponse
import ua.dp.klio.wiu.model.DetailResponse

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")id: Int, @Query("api_key")apiKey: String, @Query("language")language: String = "uk-UA"
    ):Response<DetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id")id: Int, @Query("api_key")apiKey: String, @Query("language")language: String = "uk-UA"
    ):Response<CastResponse>
}