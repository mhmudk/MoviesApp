package com.example.movies.FragmentsUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.Adapters.MoviesNowPlayinhAdapter
import com.example.movies.Adapters.MoviesPopularAdapter
import com.example.movies.Adapters.MoviesTopRatedAdapter
import com.example.movies.Details.Details
import com.example.movies.LocalDB.BaseApplication
import com.example.movies.Pojo.Movies.Result
import com.example.movies.RemoteDB.MoviesPopular.MoviesPopularViewModel
import com.example.movies.RemoteDB.MoviesTopRated.MoviesNowPlayingViewModel
import com.example.movies.RemoteDB.MoviesTopRated.MoviesViewModel
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.showToast
import com.google.firebase.auth.FirebaseAuth


class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val moviesTopRatedAdapter by lazy { MoviesTopRatedAdapter() }
    private val moviesPopularAdapter by lazy { MoviesPopularAdapter() }
    private val moviesNowPlayingAdapter by lazy { MoviesNowPlayinhAdapter() }
    private var moviePopularViewmodel = MoviesPopularViewModel()
    private var movieTopRatedViewmodel = MoviesViewModel()
    private var movieNowPlayingViewmodel = MoviesNowPlayingViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHomeBinding.inflate(inflater, container, false)


        Log.d("Test", FirebaseAuth.getInstance().currentUser?.email.toString())
        getTopRatedMovies()
        getIdFromTopRatedMovies()
        getPopularMovies()
        getIdFromPopularMovies()
        getNowPlayingMovies()
        getIdFromUpComingMovies()
        return binding.root
    }

    private fun getTopRatedMovies() {
        movieTopRatedViewmodel.getTopRatedMovies().observe(viewLifecycleOwner) {
            sentDataToRecyclerviewTopRated(it)
        }
    }

    private fun sentDataToRecyclerviewTopRated(list: List<Result>) {
        moviesTopRatedAdapter.setList(list)
        binding.recMovies.adapter = moviesTopRatedAdapter
    }

    private fun getPopularMovies() {
        moviePopularViewmodel.getPopularMovies().observe(viewLifecycleOwner) {
            sentDataToRecyclerviewPopular(it)
        }
    }

    private fun sentDataToRecyclerviewPopular(list: List<Result>) {
        moviesPopularAdapter.setList(list)
        binding.recMoviesPopular.adapter = moviesPopularAdapter
    }

    private fun getIdFromTopRatedMovies() {
        moviesTopRatedAdapter.setOnItemClick(object : MoviesTopRatedAdapter.SentDetails {
            override fun onItemClick(id: Int) {
                Log.d("Ge",id.toString())
                val intent = Intent(requireContext(), Details::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

            override fun getClickedFavourite(postion: Int) {
                var state = BaseApplication.db?.getDao()?.search(postion)
                if (state == true) {
                    showToast(requireContext(), "Already Exists")
                    return
                } else {
                    BaseApplication.db?.getDao()?.insertMoviesFav(postion)
                    showToast(requireContext(), "Movie Added")
                }
            }

        })
    }

    private fun getIdFromPopularMovies() {
        moviesPopularAdapter.setOnItemClick(object : MoviesPopularAdapter.SentDetails {

            override fun onItemClick(id: Int) {
                val intent = Intent(requireContext(), Details::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

            override fun getClickedFavourite(postion: Int) {
                Log.d("idk", id.toString())
                var state = BaseApplication.db?.getDao()?.search(postion)
                if (state == true) {
                    showToast(requireContext(), "Already Exists")
                    return
                } else {
                    BaseApplication.db?.getDao()?.insertMoviesFav(postion)
                    showToast(requireContext(), "Movie Added")
                }
            }


        })

    }
private fun getNowPlayingMovies(){
    movieNowPlayingViewmodel.getNowPlayingMovies().observe(viewLifecycleOwner){
Log.d("Data",it[0].title)
        sentDataToNowPlayingMovieAdaptrer(it)
    }
}
private fun sentDataToNowPlayingMovieAdaptrer(mList:List<Result>){
    moviesNowPlayingAdapter.setList(mList)
    binding.recMoviesNewplaying.adapter=moviesNowPlayingAdapter
    binding.recMoviesNewplaying.layoutManager = GridLayoutManager(requireContext(),2)

}
    private fun getIdFromUpComingMovies() {
        moviesNowPlayingAdapter.setOnItemClick(object : MoviesNowPlayinhAdapter.SentDetails {
            override fun onItemClick(postion: Int) {
                val intent = Intent(requireContext(), Details::class.java)
                intent.putExtra("id", postion)
                startActivity(intent)
            }

            override fun getClickedFavourite(postion: Int) {
                TODO("Not yet implemented")
            }

        })

    }

}