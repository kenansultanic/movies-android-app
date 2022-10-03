package com.example.zavrsni_v3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.FragmentFilterWatchlistBinding
import java.util.*

class FilterWatchlistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentFilterWatchlistBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_watchlist, container, false)

        val genres = resources.getStringArray(R.array.Genres)
        val spinnerGenres = binding.spinnerGenre
        val adapterGenres = ArrayAdapter(
            requireContext(),
            R.layout.spinner_list, genres
        )
        adapterGenres.setDropDownViewResource(R.layout.spinner_list)
        spinnerGenres.adapter = adapterGenres

        val years = ArrayList<String>()
        years.add("Any")
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in thisYear downTo 1900) {
            years.add(i.toString())
        }
        val spinnerYears = binding.spinnerYear
        val adapterYears = ArrayAdapter(
            requireContext(),
            R.layout.spinner_list, years
        )
        adapterYears.setDropDownViewResource(R.layout.spinner_list)
        spinnerYears.adapter = adapterYears

        binding.filterButton.setOnClickListener {
            val selectedGenre = binding.spinnerGenre.selectedItem.toString()
            val selectedYear = binding.spinnerYear.selectedItem.toString()

            val selectedID = binding.radioGroup.checkedRadioButtonId
            var sortBy: String

            when (selectedID) {
                binding.radioButtonYear.id -> sortBy = "Year"
                binding.radioButtonGenre.id -> sortBy = "Genre"
                else -> sortBy = ""
            }

            findNavController().navigate(FilterWatchlistFragmentDirections.actionFilterWatchlistFragmentToWatchListFragment(selectedGenre, selectedYear, sortBy))
        }

        return binding.root
    }
}