package com.example.zavrsni_v3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.FragmentFilterApiCallResultsBinding

class FilterApiCallResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentFilterApiCallResultsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_api_call_results, container, false)

        MovieListFragment()

        binding.searchButton.setOnClickListener{ view ->

            val searchQuery = binding.searchQuery.text.toString()

            if (searchQuery.isEmpty()) {
                Toast.makeText(requireContext(), "Search query cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val selectedID = binding.radioGroup.checkedRadioButtonId
            var contentType: String

            when (selectedID) {
                binding.radioButtonMovies.id -> contentType = "movie"
                binding.radioButtonSeries.id -> contentType = "series"
                binding.radioButtonEpisode.id -> contentType = "episode"
                else -> contentType = ""
            }

            view.findNavController().navigate(FilterApiCallResultsFragmentDirections.actionFilterApiCallResultsFragmentToMovieListFragment(searchQuery, contentType, true))
        }

        return binding.root
    }
}