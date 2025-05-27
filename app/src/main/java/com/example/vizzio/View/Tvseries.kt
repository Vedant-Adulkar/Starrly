package com.example.vizzio.View

import TvSeriesPopAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vizzio.Model.popdata
import com.example.vizzio.ViewModel.TvSeriesViewModel
import com.example.vizzio.databinding.FragmentSeriesBinding

class Tvseries : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private lateinit var viewModel: TvSeriesViewModel
    private lateinit var tvSeriesAdapter: TvSeriesPopAdapter

    private var fullTvSeriesList: List<popdata> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTVSeriesRecyclerView()

        viewModel = ViewModelProvider(this)[TvSeriesViewModel::class.java]

        viewModel.observeTvSeriesLiveData().observe(viewLifecycleOwner, Observer { seriesList ->
            fullTvSeriesList = seriesList
            tvSeriesAdapter.setTvSeriesList(seriesList)
        })

        viewModel.getPopularTvSeries()

        binding.filterSeries.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterTVSeries(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupTVSeriesRecyclerView() {
        tvSeriesAdapter = TvSeriesPopAdapter { selectedSeries ->
            val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra("poster_path", selectedSeries.poster_path)
                putExtra("name", selectedSeries.name ?: selectedSeries.original_name)
                putExtra("rating", selectedSeries.vote_average.toString())
                putExtra("duration", "N/A")
                putExtra("overview", selectedSeries.overview)
            }
            startActivity(intent)
        }

        binding.rvTvSeries.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tvSeriesAdapter
        }
    }

    private fun filterTVSeries(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullTvSeriesList
        } else {
            fullTvSeriesList.filter { series ->
                series.name?.contains(query, ignoreCase = true) == true
            }
        }
        tvSeriesAdapter.setTvSeriesList(filteredList)
    }
}
