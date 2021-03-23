package rtchavez.challenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rtchavez.challenge.R
import rtchavez.challenge.data.model.Upcoming
import rtchavez.challenge.databinding.UpcomingListItemBinding

class UpcomingDetailAdapter() : RecyclerView.Adapter<UpcomingDetailViewHolder>() {

    var results: List<Upcoming> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.upcoming_list_item -> UpcomingDetailViewHolder.UpcomingViewHolder(
                UpcomingListItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: UpcomingDetailViewHolder, position: Int) {
        when (holder) {
            is UpcomingDetailViewHolder.UpcomingViewHolder -> holder.binding.apply {
                val result = results[position] as Upcoming
                upcomingName.text = result.name
                upcomingDates.text = "${result.startDate} - ${result.endDate}"
                val venue = listOf(result.venue.city, result.venue.state)
                    .filterNotNull()
                    .joinToString(separator = ", ")
                upcomingVenue.text = venue
                upcomingVenue.visibility = if (venue.isEmpty()) View.GONE else View.VISIBLE

                Glide.with(root.context)
                    .load(result.icon)//.apply(RequestOptions().fitCenter().circleCrop())
                    .into(upcomingIcon)
            }
        }
    }

    override fun getItemViewType(position: Int) = R.layout.upcoming_list_item

    override fun getItemCount() = results.size
}

sealed class UpcomingDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class UpcomingViewHolder(val binding: UpcomingListItemBinding)
        : UpcomingDetailViewHolder(binding.root)
}