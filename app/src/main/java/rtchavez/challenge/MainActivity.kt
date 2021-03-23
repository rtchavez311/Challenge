package rtchavez.challenge

import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.COLOR_SCHEME_DARK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import rtchavez.challenge.data.api.ChallengeService
import rtchavez.challenge.data.model.Data
import rtchavez.challenge.data.model.Upcoming
import rtchavez.challenge.databinding.ActivityMainBinding
import rtchavez.challenge.ui.ChallengesViewModel
import rtchavez.challenge.ui.UpcomingDetailAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<ChallengesViewModel>()
    private val adapter by lazy { UpcomingDetailAdapter(viewModel) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.results.adapter = adapter
        viewModel.results.observe(this) { results ->
            handleResults(results)
        }
        viewModel.clickedUrl.observe(this) { clickedUrl ->
            val typedValue = TypedValue()
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            val toolbarColor = typedValue.data
            theme.resolveAttribute(R.attr.colorPrimaryVariant, typedValue, true)
            val secondaryToolbarColor = typedValue.data
            theme.resolveAttribute(R.attr.colorPrimaryVariant, typedValue, true)
            val navigationBarColor = typedValue.data

            val params = CustomTabColorSchemeParams.Builder()
                .setToolbarColor(toolbarColor)
                .setSecondaryToolbarColor(secondaryToolbarColor)
                .setNavigationBarColor(navigationBarColor)
                .build()

            val url = "${ChallengeService.baseUrl}$clickedUrl"
            val intent = CustomTabsIntent.Builder()
                .setColorSchemeParams(COLOR_SCHEME_DARK, params)
                .build()
            intent.launchUrl(this@MainActivity, Uri.parse(url))
        }
        viewModel.loadUpcomingData()
    }

    private fun handleResults(results: Data<Upcoming>) {
        adapter.results = results.data
    }
}