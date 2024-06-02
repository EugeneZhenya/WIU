package ua.dp.klio.wiu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.dp.klio.wiu.api.TmdbRepo

class DetailViewModelFactory(val repo: TmdbRepo, val movieId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repo, movieId) as T
    }
}