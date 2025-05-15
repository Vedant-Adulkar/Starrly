import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vizzio.Model.MoviesItem
import com.example.vizzio.databinding.MovieslayoutBinding

class MoviesAdapter(
    private val onMovieClick: (MoviesItem) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movieList = ArrayList<MoviesItem>()

    fun setMovieList(movies: List<MoviesItem>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MovieslayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieslayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]

        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(holder.binding.movieImage)

        holder.binding.movieName.text = movie.name


        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }

    override fun getItemCount(): Int = movieList.size
}
