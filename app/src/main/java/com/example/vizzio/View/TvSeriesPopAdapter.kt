import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vizzio.Model.popdata

import com.example.vizzio.databinding.TvserieslayoutBinding

class TvSeriesPopAdapter(
    private val onTvSeriesClick: (popdata) -> Unit
) : RecyclerView.Adapter<TvSeriesPopAdapter.ViewHolder>() {

    private val tvSeriesList = ArrayList<popdata>()

    fun setTvSeriesList(series: List<popdata>) {
        tvSeriesList.clear()
        tvSeriesList.addAll(series)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: TvserieslayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TvserieslayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val series = tvSeriesList[position]

        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500${series.poster_path}")
            .into(holder.binding.tvSeriesImage)

        holder.binding.tvSeriesName.text = series.name

        holder.itemView.setOnClickListener {
            onTvSeriesClick(series)
        }
    }

    override fun getItemCount(): Int = tvSeriesList.size
}
