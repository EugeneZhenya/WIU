package ua.dp.klio.wiu.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.dp.klio.wiu.model.CastResponse
import ua.dp.klio.wiu.model.DetailResponse
import ua.dp.klio.wiu.utils.Constants.API_KEY

class TmdbRepo(val service: ApiService) {
    val detailData = MutableLiveData<Response<DetailResponse>>()
    val castData = MutableLiveData<Response<CastResponse>>()

    val movieDetail: LiveData<Response<DetailResponse>>
        get() = detailData

    val castDetail: LiveData<Response<CastResponse>>
        get() = castData

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

    suspend fun getMovieCast(id: Int) {
        val result = service.getMovieCast(id, API_KEY)

        try {
            if (result.body() != null) {
                castData.postValue(Response.Success(result.body()))
            } else {
                castData.postValue(Response.Error(result.errorBody().toString()))
            }
        } catch (e: Exception) {
            castData.postValue(Response.Error(e.message.toString()))
        }
    }
}