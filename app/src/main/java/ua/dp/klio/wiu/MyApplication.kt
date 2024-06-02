package ua.dp.klio.wiu

import android.app.Application
import ua.dp.klio.wiu.api.ApiService
import ua.dp.klio.wiu.api.RetrofitHelper
import ua.dp.klio.wiu.api.TmdbRepo

class MyApplication : Application() {
    lateinit var tmdbRepo : TmdbRepo

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        val service = RetrofitHelper.getInstance().create(ApiService::class.java)
        tmdbRepo = TmdbRepo(service)
    }
}