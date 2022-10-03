package com.example.zavrsni_v3.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.ui.fragments.MovieListFragmentDirections
import com.example.zavrsni_v3.ui.fragments.WatchListFragmentDirections

class MovieAdapter(private val fromDatabase: Boolean, var isOnline: Boolean): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private var movieList = emptyList<Movie>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]

        holder.itemView.apply {
            rootView.findViewById<TextView>(R.id.item_name).text = movie.Title
            Glide.with(this).load(movie.Poster).into(this.rootView.findViewById(R.id.item_background))
            rootView.setOnClickListener {

                if(!isOnline) {
                    Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (fromDatabase)
                    findNavController().navigate(WatchListFragmentDirections.actionWatchListFragmentToOverviewFragment(movie.imdbID, true))
                else
                    findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToOverviewFragment(movie.imdbID, false))
            }
        }
    }

    override fun getItemCount() = movieList.size

    fun setData(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

}