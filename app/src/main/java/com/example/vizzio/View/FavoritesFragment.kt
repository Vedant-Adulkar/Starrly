package com.example.vizzio.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vizzio.ViewModel.FavoriteViewModel
import com.example.vizzio.databinding.FragmentFavouriteBinding

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteMoviesAdapter { movie ->
            // Handle movie click - navigate to details
            val intent = android.content.Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.id)
                putExtra(DetailsActivity.EXTRA_NAME, movie.title)
                putExtra(DetailsActivity.EXTRA_POSTER_PATH, movie.posterPath)
                putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
                putExtra(DetailsActivity.EXTRA_RATING, movie.rating.toString())
            }
            startActivity(intent)
        }

        binding.favRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
        }
    }

    private fun observeFavorites() {
        viewModel.allFavoriteMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
    }
} 