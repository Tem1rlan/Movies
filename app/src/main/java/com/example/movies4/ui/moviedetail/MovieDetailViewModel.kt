package com.example.movies4.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.movies4.data.entities.Movie
import com.example.movies4.data.repository.MovieRepository
import com.example.movies4.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
        private val repository: MovieRepository
) : ViewModel() {

    val id = MutableLiveData<Int>()

    private val _movie = id.switchMap { repository.getMovie(it) }
    val movie: LiveData<Resource<Movie>> = _movie
}