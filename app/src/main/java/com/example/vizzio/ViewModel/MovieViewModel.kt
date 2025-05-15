import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vizzio.Model.MoviesItem
import com.example.vizzio.Model.movies
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MovieViewModel : ViewModel() {
    private var movieLiveData = MutableLiveData<List<MoviesItem>>()
    fun getPopularMovies() {
        RetrofitInstance.api.getPopularMovies("285447fc233b48898616ca17c4841cd1").enqueue(object  : Callback<movies>{
            override fun onResponse(call: Call<movies>, response: Response<movies>) {
                if (response.body()!=null){
                    movieLiveData.value = response.body()!!.results
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<movies>, t: Throwable) {
                Log.d("TAG",t.message.toString())
            }
        })
    }
    fun observeMovieLiveData() : LiveData<List<MoviesItem>> {
        return movieLiveData
    }
}