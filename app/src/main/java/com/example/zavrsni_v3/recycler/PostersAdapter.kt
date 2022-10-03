package com.example.zavrsni_v3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.zavrsni_v3.R
import com.example.zavrsni_v3.model.Movie


internal class PostersAdapter(private val context: Context): BaseAdapter() {

    private var movies = emptyList<Movie>()
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView

    override fun getCount() = movies.size

    override fun getItem(position: Int) = null

    override fun getItemId(position: Int) = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        var convertView_ = convertView
        if (layoutInflater == null)
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView_ == null)
            convertView_ = layoutInflater!!.inflate(R.layout.poster_grid_item, null)

        imageView = convertView_!!.findViewById(R.id.imageView)
        Glide.with(context)
            .load(movies[position].Poster)
            .into(imageView)
        return convertView_
    }

    fun setData(movies: List<Movie>) {
        this.movies = movies
    }
}
