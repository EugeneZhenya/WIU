package ua.dp.klio.wiu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.dp.klio.wiu.api.Response
import ua.dp.klio.wiu.api.TmdbRepo
import ua.dp.klio.wiu.model.CastResponse
import ua.dp.klio.wiu.model.DetailResponse

class DetailViewModel(val repo: TmdbRepo, id: Int) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMovieDetails(id)
            repo.getMovieCast(id)
        }
    }

    val movieDetails: LiveData<Response<DetailResponse>>
        get() = repo.movieDetail

    val castDetails: LiveData<Response<CastResponse>>
        get() = repo.castDetail
}