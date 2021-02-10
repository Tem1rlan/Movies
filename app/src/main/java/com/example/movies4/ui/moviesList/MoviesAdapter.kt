package com.example.movies4.ui.moviesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies4.data.entities.Movie
import com.example.movies4.databinding.MovieItemBinding
import com.example.movies4.utils.Constants
import com.example.movies4.utils.loadImage

class MoviesAdapter(
    private val listener: MovieItemListener
): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    interface MovieItemListener {
        fun onClickedMovie(movieId: Int, binding: MovieItemBinding)
    }

    private val items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: MovieItemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class MovieViewHolder(
            private val itemBinding: MovieItemBinding,
            private val listener: MovieItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private lateinit var movie: Movie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Movie) {
            this.movie = item
            itemBinding.movieItemTitle.text = item.title
            itemBinding.movieItemRelease.text = item.releaseDate
            itemBinding.movieItemRatingbar.rating = item.voteAverage.toFloat()
            itemBinding.movieItemPoster.loadImage(Constants.IMAGE_URL + item.posterPath, itemBinding.progressBar)
        }

        override fun onClick(v: View?) {
            listener.onClickedMovie(movie.id, itemBinding)
        }

    }
}