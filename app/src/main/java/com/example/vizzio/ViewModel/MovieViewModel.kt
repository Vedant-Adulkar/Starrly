import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vizzio.Model.MoviesItem
import com.example.vizzio.Model.Toprated
import com.example.vizzio.Model.Upcoming
import com.example.vizzio.Model.movies
import com.example.vizzio.Model.topdata
import com.example.vizzio.Model.upcommingdata
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val popularMovieLiveData = MutableLiveData<List<MoviesItem>>()
    private val upcomingMovieLiveData = MutableLiveData<List<upcommingdata>>()
    private val topRatedMovieLiveData = MutableLiveData<List<topdata>>()  // Added top rated

    fun getPopularMovies() {
        RetrofitInstance.api.getPopularMovies("285447fc233b48898616ca17c4841cd1")
            .enqueue(object : Callback<movies> {
                override fun onResponse(call: Call<movies>, response: Response<movies>) {
                    if (response.body() != null) {
                        popularMovieLiveData.value = response.body()!!.results
                    }
                }

                override fun onFailure(call: Call<movies>, t: Throwable) {
                    Log.d("PopularMovies", t.message.toString())
                }
            })
    }

    fun getUpcomingMovies() {
        RetrofitInstance.api.getUpcomingMovies("285447fc233b48898616ca17c4841cd1")
            .enqueue(object : Callback<Upcoming> {
                override fun onResponse(call: Call<Upcoming>, response: Response<Upcoming>) {
                    if (response.body() != null) {
                        upcomingMovieLiveData.value = response.body()!!.results
                    }
                }

                override fun onFailure(call: Call<Upcoming>, t: Throwable) {
                    Log.d("UpcomingMovies", t.message.toString())
                }
            })
    }

    // New method for Top Rated movies
    fun getTopRatedMovies() {
        RetrofitInstance.api.getTopRatedMovies("285447fc233b48898616ca17c4841cd1")
            .enqueue(object : Callback<Toprated> {
                override fun onResponse(call: Call<Toprated>, response: Response<Toprated>) {
                    if (response.body() != null) {
                        topRatedMovieLiveData.value = response.body()!!.results
                    }
                }

                override fun onFailure(call: Call<Toprated>, t: Throwable) {
                    Log.d("TopRatedMovies", t.message.toString())
                }
            })
    }

    fun observePopularMovies(): LiveData<List<MoviesItem>> {
        return popularMovieLiveData
    }

    fun observeUpcomingMovies(): LiveData<List<upcommingdata>> {
        return upcomingMovieLiveData
    }

    // New observer for top rated movies
    fun observeTopRatedMovies(): LiveData<List<topdata>> {
        return topRatedMovieLiveData
    }
}
