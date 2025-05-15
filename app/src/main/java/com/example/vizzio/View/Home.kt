import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vizzio.Model.MoviesItem
import com.example.vizzio.View.DetailsActivity
import com.example.vizzio.databinding.FragmentHomeBinding

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        prepareRecyclerView()

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)


        viewModel.observeMovieLiveData().observe(viewLifecycleOwner, Observer { movieList ->
            movieAdapter.setMovieList(movieList)
        })

        viewModel.getPopularMovies()
    }

    private fun prepareRecyclerView() {
        movieAdapter = MoviesAdapter { selectedMovie ->

            val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra("poster_path", selectedMovie.poster_path)
                putExtra("name", selectedMovie.original_name)
                putExtra("rating", selectedMovie.vote_average.toString())
                putExtra("duration",  "N/A")
                putExtra("overview", selectedMovie.overview)
            }
            startActivity(intent)
        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            adapter = movieAdapter
        }

    }
}
