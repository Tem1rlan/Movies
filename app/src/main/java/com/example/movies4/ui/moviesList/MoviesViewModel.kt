package com.example.movies4.ui.moviesList

import androidx.lifecycle.ViewModel
import com.example.movies4.data.repository.MovieRepository
import com.example.movies4.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MoviesViewModel @Inject constructor(
    repository: MovieRepository
) : ViewModel() {
    val movies = repository.getMovies()
}