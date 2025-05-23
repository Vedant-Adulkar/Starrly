import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vizzio.Model.MoviesItem
import com.example.vizzio.Model.upcommingdata
import com.example.vizzio.View.DetailsActivity
import com.example.vizzio.View.Uppcoming_movie_adapter
import com.example.vizzio.View.Topratedadapter  // <-- Add this import
import com.example.vizzio.databinding.FragmentHomeBinding
import android.text.TextWatcher
import android.text.Editable
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vizzio.Model.topdata

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel

    private var fullPopularMovieList: List<MoviesItem> = emptyList()
    private var fullUpcomingMovieList: List<upcommingdata> = emptyList()
    private var fullTopRatedMovieList: List<topdata> = emptyList()    // <-- For top rated

    private lateinit var popularMovieAdapter: MoviesAdapter
    private lateinit var upcomingMovieAdapter: Uppcoming_movie_adapter
    private lateinit var topRatedMovieAdapter: Topratedadapter    // <-- Top rated adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPopularMoviesRecyclerView()
        setupUpcomingMoviesRecyclerView()
        setupTopRatedMoviesRecyclerView()  // <-- Setup Top Rated RecyclerView

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Observe Popular Movies LiveData
        viewModel.observePopularMovies().observe(viewLifecycleOwner, Observer { movieList ->
            fullPopularMovieList = movieList
            popularMovieAdapter.setMovieList(movieList)
        })

        // Observe Upcoming Movies LiveData
        viewModel.observeUpcomingMovies().observe(viewLifecycleOwner, Observer { upcomingList ->
            fullUpcomingMovieList = upcomingList
            upcomingMovieAdapter.setMovieList(upcomingList)
        })

        // Observe Top Rated Movies LiveData
        viewModel.observeTopRatedMovies().observe(viewLifecycleOwner, Observer { topRatedList ->
            fullTopRatedMovieList = topRatedList
            topRatedMovieAdapter.setMovieList(topRatedList)
        })

        // Fetch movies
        viewModel.getPopularMovies()
        viewModel.getUpcomingMovies()
        viewModel.getTopRatedMovies()   // <-- Fetch top rated movies

        // Single Search bar listener filtering all 3 lists
        binding.filter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                filterPopularMovies(query)
                filterUpcomingMovies(query)
                filterTopRatedMovies(query)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupPopularMoviesRecyclerView() {
        popularMovieAdapter = MoviesAdapter { selectedMovie ->
            val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra("poster_path", selectedMovie.poster_path)
                putExtra("name", selectedMovie.original_title ?: selectedMovie.original_title)
                putExtra("rating", selectedMovie.vote_average.toString())
                putExtra("duration", "N/A")
                putExtra("overview", selectedMovie.overview)
            }
            startActivity(intent)
        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMovieAdapter
        }
    }

    private fun setupUpcomingMoviesRecyclerView() {
        upcomingMovieAdapter = Uppcoming_movie_adapter { selectedMovie ->
            val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra("poster_path", selectedMovie.poster_path)
                putExtra("name", selectedMovie.original_title)
                putExtra("rating", selectedMovie.vote_average.toString())
                putExtra("duration", "N/A")
                putExtra("overview", selectedMovie.overview)
            }
            startActivity(intent)
        }

        binding.uppcomingrecy.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMovieAdapter
        }
    }

    private fun setupTopRatedMoviesRecyclerView() {
        topRatedMovieAdapter = Topratedadapter { selectedMovie ->
            val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra("poster_path", selectedMovie.poster_path)
                putExtra("name", selectedMovie.original_title ?: selectedMovie.original_title)
                putExtra("rating", selectedMovie.vote_average.toString())
                putExtra("duration", "N/A")
                putExtra("overview", selectedMovie.overview)
            }
            startActivity(intent)
        }

        binding.toprecy.apply {   // <-- Replace this with the correct id for your top rated RecyclerView in layout
            layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns grid
            adapter = topRatedMovieAdapter
        }
    }

    private fun filterPopularMovies(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullPopularMovieList
        } else {
            fullPopularMovieList.filter { movie ->
                movie.original_title?.contains(query, ignoreCase = true) == true
            }
        }
        popularMovieAdapter.setMovieList(filteredList)
    }

    private fun filterUpcomingMovies(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullUpcomingMovieList
        } else {
            fullUpcomingMovieList.filter { movie ->
                movie.original_title?.contains(query, ignoreCase = true) == true
            }
        }
        upcomingMovieAdapter.setMovieList(filteredList)
    }

    private fun filterTopRatedMovies(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullTopRatedMovieList
        } else {
            fullTopRatedMovieList.filter { movie ->
                movie.original_title?.contains(query, ignoreCase = true) == true
            }
        }
        topRatedMovieAdapter.setMovieList(filteredList)
    }
}
