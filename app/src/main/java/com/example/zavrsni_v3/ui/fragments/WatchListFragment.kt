package com.example.zavrsni_v3.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.FragmentWatchlistBinding
import com.example.zavrsni_v3.model.MainViewModel
import com.example.zavrsni_v3.model.MainViewModelFactory
import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.model.MovieDetails
import com.example.zavrsni_v3.recycler.MovieAdapter
import com.example.zavrsni_v3.repository.Repository
import com.example.zavrsni_v3.util.Parse

class WatchListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val adapter by lazy { MovieAdapter(true, true) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentWatchlistBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_watchlist, container, false)
        val args: WatchListFragmentArgs by navArgs()

        val repository = Repository(context as Context)
        val viewModelFactory = MainViewModelFactory(repository)

        val recycler = binding.recycler
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getMoviesFiltered(args.selectedGenre, args.selectedYear, args.sortBy)

        viewModel.allSavedMoviesFiltered.observe(viewLifecycleOwner, Observer { movies ->
            adapter.setData(Parse.parseMovies(movies))
        })

        return binding.root
    }

}