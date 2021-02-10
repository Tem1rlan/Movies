package com.example.movies4.data.remote

import com.example.movies4.utils.Constants
import java.util.concurrent.ConcurrentSkipListMap
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
): BaseDataSource() {

    suspend fun getMovies() = getResult { movieService.getPopularMovies(Constants.API_KEY) }

    suspend fun getMovie(id: Int) = getResult { movieService.getMovie(id, Constants.API_KEY) }
}