package com.example.zavrsni_v3.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.FragmentOverviewBinding
import com.example.zavrsni_v3.model.MainViewModel
import com.example.zavrsni_v3.model.MainViewModelFactory
import com.example.zavrsni_v3.model.MovieDetails
import com.example.zavrsni_v3.repository.Repository

class OverviewFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var currentMovie: MovieDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: OverviewFragmentArgs by navArgs()

        val binding: FragmentOverviewBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_overview, container, false)
        val repository = Repository(context as Context)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        if (args.fromWatchlist) {

            binding.removeFromWatchlist.isVisible = true
            binding.addToWatchlist.isVisible = false

            viewModel.getMovieFromDatabase(args.imdbID)
            viewModel.movie.observe(viewLifecycleOwner, Observer {
                setData(binding, it)
            })

        } else {

            binding.removeFromWatchlist.isVisible = false
            binding.addToWatchlist.isVisible = true

            viewModel.getMovie(args.imdbID)

            viewModel.responseMovieDetails.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    setData(binding, response.body()!!)
                }
            })
        }

        binding.addToWatchlist.setOnClickListener {
            viewModel.addMovieToWatchLater(currentMovie)
            binding.removeFromWatchlist.isVisible = true
            binding.addToWatchlist.isVisible = false
            Toast.makeText(requireContext(), "Added to Watchlist", Toast.LENGTH_LONG).show()
        }

        binding.removeFromWatchlist.setOnClickListener {
            viewModel.deleteMovieFromDatabase(currentMovie)
            binding.removeFromWatchlist.isVisible = false
            binding.addToWatchlist.isVisible = true
            Toast.makeText(requireContext(), "Removed from Watchlist", Toast.LENGTH_LONG).show()
        }

        binding.suggestButton.setOnClickListener {
            val poruka = "I suggest you watch " + currentMovie.Title + ", it's really good!"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, poruka)

            startActivity(shareIntent)
        }

        return binding.root
    }


    private fun setData(binding: FragmentOverviewBinding, movie: MovieDetails) {

        currentMovie = movie

        binding.itemHeader.text = movie.Title
        binding.itemActors.text = movie.Actors
        binding.itemAwards.text = movie.Awards
        binding.itemDescription.text = movie.Plot
        binding.itemGenre.text = movie.Genre
        binding.itemLanguage.text = movie.Language
        binding.itemRating.text = movie.imdbRating
        binding.itemRuntime.text = movie.Runtime
        binding.itemWriter.text = movie.Writer
        binding.itemYear.text = movie.Year
        Glide.with(this).load(movie.Poster).into(binding.itemImage)
    }
}