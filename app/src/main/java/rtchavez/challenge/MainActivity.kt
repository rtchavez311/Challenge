package rtchavez.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import rtchavez.challenge.data.model.Data
import rtchavez.challenge.data.model.Upcoming
import rtchavez.challenge.databinding.ActivityMainBinding
import rtchavez.challenge.ui.ChallengesViewModel
import rtchavez.challenge.ui.UpcomingDetailAdapter

class MainActivity : AppCompatActivity() {

    private val adapter = UpcomingDetailAdapter()
    private val viewModel by viewModel<ChallengesViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.results.adapter = adapter
        viewModel.results.observe(this) { results ->
            handleResults(results)
        }
        viewModel.loadUpcomingData()
    }

    private fun handleResults(results: Data<Upcoming>) {
        adapter.results = results.data
    }
}