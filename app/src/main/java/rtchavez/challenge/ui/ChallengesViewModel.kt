package rtchavez.challenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rtchavez.challenge.data.model.Data
import rtchavez.challenge.data.model.Upcoming
import rtchavez.challenge.data.repository.ChallengeRepository

class ChallengesViewModel(private val repository: ChallengeRepository) : ViewModel() {

    val results: MutableLiveData<Data<Upcoming>> = MutableLiveData()
    val clickedUrl: LiveData<String> = MutableLiveData()

    fun loadUpcomingData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getUpcomingData()
            withContext(Dispatchers.Main) {
                results.value = data
            }
        }
    }

    fun onUrlClicked(url: String) {
        (clickedUrl as MutableLiveData).postValue(url)
    }

}