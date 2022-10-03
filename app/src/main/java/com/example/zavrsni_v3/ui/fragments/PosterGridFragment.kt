package com.example.zavrsni_v3.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.PosterGridBinding
import com.example.zavrsni_v3.model.MainViewModel
import com.example.zavrsni_v3.model.MainViewModelFactory
import com.example.zavrsni_v3.recycler.MovieAdapter
import com.example.zavrsni_v3.recycler.PostersAdapter
import com.example.zavrsni_v3.repository.Repository
import com.example.zavrsni_v3.util.Parse

class PosterGridFragment : Fragment() {

    private lateinit var gridView: GridView
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: PosterGridBinding = DataBindingUtil.inflate(inflater, R.layout.poster_grid, container, false)

        val repository = Repository(context as Context)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        gridView = binding.gridView
        val postersAdapter = PostersAdapter(requireContext())

        viewModel.allSavedMovies.observe(viewLifecycleOwner, Observer { movies ->
            postersAdapter.setData(Parse.parseMovies(movies))
            gridView.adapter = postersAdapter
            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(requireContext(), movies[+position].Title, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}