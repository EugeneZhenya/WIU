package ua.dp.klio.wiu.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.dp.klio.wiu.model.DetailResponse
import ua.dp.klio.wiu.utils.Constants.API_KEY

class TmdbRepo(val service: ApiService) {
    val detailData = MutableLiveData<Response<DetailResponse>>()
    val movieDetail: LiveData<Response<DetailResponse>>
        get() = detailData

    suspend fun getMovieDetails(id: Int) {
        val result = service.getMovieDetails(id, API_KEY)

        try {
            if (result.body() != null) {
                detailData.postValue(Response.Success(result.body()))
            } else {
                detailData.postValue(Response.Error(result.errorBody().toString()))
            }
        } catch (e: Exception) {
            detailData.postValue(Response.Error(e.message.toString()))
        }
    }
}