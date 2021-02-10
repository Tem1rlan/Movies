package com.example.movies4.data.remote

import com.example.movies4.data.entities.Movie
import com.example.movies4.data.entities.MovieResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String): Response<MovieResults>

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int, @Query("api_key") api_key: String): Response<Movie>
}