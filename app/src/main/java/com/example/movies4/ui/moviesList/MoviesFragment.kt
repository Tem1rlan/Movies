package com.example.movies4.ui.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies4.R
import com.example.movies4.databinding.MovieItemBinding
import com.example.movies4.databinding.MoviesFragmentBinding
import com.example.movies4.utils.Resource
import com.example.movies4.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {

    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(this)
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.movieRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.movies.observe(viewLifecycleOwner, {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        adapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR ->
                    it.message?.let { it1 -> toast(requireContext(), it1) }

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedMovie(movieId: Int, binding: MovieItemBinding) {
        findNavController().navigate(
                R.id.action_moviesFragment_to_movieDetailFragment,
                bundleOf("id" to movieId)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}