package com.example.movies4.data.repository

import com.example.movies4.data.local.MovieDao
import com.example.movies4.data.remote.MovieRemoteDataSource
import com.example.movies4.utils.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getMovies() = performGetOperation(
        databaseQuery = { localDataSource.getMovies() },
        networkCall = { remoteDataSource.getMovies() },
        saveCallResult = { localDataSource.insertAll(it.movies) }
    )

    fun getMovie(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getMovie(id) },
        networkCall = { remoteDataSource.getMovie(id) },
        saveCallResult = { localDataSource.insert(it) }
    )
}