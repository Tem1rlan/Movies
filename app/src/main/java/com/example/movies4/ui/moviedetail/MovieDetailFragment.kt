package com.example.movies4.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movies4.data.entities.Movie
import com.example.movies4.databinding.MovieDetailFragmentBinding
import com.example.movies4.utils.Constants
import com.example.movies4.utils.Resource
import com.example.movies4.utils.loadImage
import com.example.movies4.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val model: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { model.id.postValue(it) }
        setupObservers()
    }

    private fun setupObservers() {
        model.movie.observe(viewLifecycleOwner, {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    bindMovie(it.data!!)
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.ERROR ->
                    it.message?.let { it1 -> toast(requireContext(), it1) }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun bindMovie(movie: Movie) {
        binding.movieItemTitle.text = movie.title
        binding.detailDesc.text = movie.overview
        binding.movieItemRelease.text = movie.releaseDate
        binding.movieItemRatingbar.rating = movie.voteAverage.toFloat()
        binding.movieItemPoster.loadImage(Constants.IMAGE_URL + movie.posterPath, binding.imageProgressBar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}