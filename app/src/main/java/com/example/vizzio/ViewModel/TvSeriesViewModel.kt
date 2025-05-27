package com.example.vizzio.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vizzio.Model.TvInstance
import com.example.vizzio.Model.TvSeriesPop
import com.example.vizzio.Model.popdata
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class TvSeriesViewModel : ViewModel() {
    private var tvSeriesLiveData = MutableLiveData<List<popdata>>()

    fun getPopularTvSeries() {
        TvInstance.api.getPopularTvSeries("285447fc233b48898616ca17c4841cd1")
            .enqueue(object : Callback<TvSeriesPop> {
                override fun onResponse(call: Call<TvSeriesPop>, response: Response<TvSeriesPop>) {
                    if (response.body() != null) {
                        tvSeriesLiveData.value = response.body()!!.results
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<TvSeriesPop>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }
            })
    }

    fun observeTvSeriesLiveData(): LiveData<List<popdata>> {
        return tvSeriesLiveData
    }
}
