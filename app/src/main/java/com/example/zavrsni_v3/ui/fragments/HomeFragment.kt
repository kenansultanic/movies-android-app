package com.example.zavrsni_v3.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.databinding.FragmentHomeBinding

@RequiresApi(Build.VERSION_CODES.M)
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)
        val hasInternetAccess = hasInternetAccess(requireContext())

        binding.search.setOnClickListener {

            if (hasInternetAccess)
                findNavController().navigate(HomeFragmentDirections.actionHomeTemporaryToFilterApiCallResultsFragment())
            else
                findNavController().navigate(HomeFragmentDirections.actionHomeTemporaryToMovieListFragment(null, null, false))
        }

        binding.watchlist.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFilterWatchlistFragment())
        }

        binding.posterGallery.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPosterGridFragment())
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetAccess(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return true
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                return true
        }
        return false
    }

}