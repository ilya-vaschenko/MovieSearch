package com.example.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.GrayscaleTransformation
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.R
import com.example.moviesearch.model.Film
import kotlinx.android.synthetic.main.item_film.view.*

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    var filmList: List<Film> = ArrayList()

    var listener: OnItemViewClickListener? = null

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(film: Film) {

            itemView.apply {
                title.text = film.name
                date.text = film.date.toString()
                imageView.setImageResource(film.imageIndex)
                setOnClickListener {
                    listener?.onItemClick(film)
                }
                imageView.load("https://image.tmdb.org/t/p/w500${film.posterPath}?api_key=${BuildConfig.FILM_API_KEY}") {
                    crossfade(true)
                    transformations(GrayscaleTransformation())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun getItemCount(): Int = filmList.size

    internal fun setFilmList(filmList: List<Film>) {
        this.filmList = filmList
        notifyDataSetChanged()
    }

    fun interface OnItemViewClickListener {
        fun onItemClick(film: Film)
    }
}