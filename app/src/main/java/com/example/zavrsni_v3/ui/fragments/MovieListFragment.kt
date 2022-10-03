package com.example.zavrsni_v3.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.FragmentMovieListBinding
import com.example.zavrsni_v3.model.MainViewModel
import com.example.zavrsni_v3.model.MainViewModelFactory
import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.recycler.MovieAdapter
import com.example.zavrsni_v3.repository.Repository

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val adapter by lazy { MovieAdapter(false, true) }
    private lateinit var apiCallMovies: List<Movie>
    private var apiCallSuccesfull = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMovieListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_list, container, false)
        val args: MovieListFragmentArgs by navArgs()

        val repository = Repository(context as Context)
        val viewModelFactory = MainViewModelFactory(repository)

        val recycler = binding.recycler
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        if (args.hasInternetAccess) {

            viewModel.getMovies(args.searchQuery!!, args.type!!)

            viewModel.responseMoviesList.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    if (response.body()?.Search == null) {
                        apiCallSuccesfull = false
                        Toast.makeText(requireContext(), "No results found", Toast.LENGTH_LONG).show()
                        findNavController().navigateUp()
                        return@Observer
                    }
                    apiCallSuccesfull = true
                    response.body()?.let {
                        apiCallMovies = it.Search
                        adapter.setData(it.Search)
                    }
                } else {
                    Log.e("error", response.errorBody().toString())
                }
            })
        }
        else {

            adapter.isOnline = false

            viewModel.moviesCache.observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if (adapter.isOnline && apiCallSuccesfull) {
            viewModel.deleteCache()
            for (movie in apiCallMovies)
                viewModel.addMoviesToCache(movie)
        }
    }
}