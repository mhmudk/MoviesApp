package com.example.movies.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.Pojo.Const
import com.example.movies.Pojo.Favourite

import com.example.movies.R
import com.example.movies.Pojo.Movies.Result
import com.example.movies.Pojo.MoviesId.MoviesID
import java.util.*

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    var movieslist: List<Favourite> = emptyList()

    fun setList(data: List<Favourite>) {
        this.movieslist = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_favoruite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.ViewHolder, position: Int) {
        var data: Favourite= movieslist[position]
        holder.setId(data)
    }

    override fun getItemCount(): Int {
        return movieslist.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.id_movies_fav)
        val titel: TextView = itemView.findViewById(R.id.name_of_movies_fav)
        val overview: TextView = itemView.findViewById(R.id.overview_movies_fav)
        val imgDelted: ImageView = itemView.findViewById(R.id.imgFav)

        fun setId(data: Favourite) {
            Glide.with(img.context)
                .load(Const.BASE_URL_IMG + data.poster_path)
                .into(img)
            titel.text = data.title
            overview.text = data.overview

        }

    }
}