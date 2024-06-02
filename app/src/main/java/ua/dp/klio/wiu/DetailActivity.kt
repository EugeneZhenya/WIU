package ua.dp.klio.wiu

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ua.dp.klio.wiu.api.Response
import ua.dp.klio.wiu.databinding.ActivityDetailBinding
import ua.dp.klio.wiu.model.DetailResponse
import ua.dp.klio.wiu.viewmodel.DetailViewModel
import ua.dp.klio.wiu.viewmodel.DetailViewModelFactory

class DetailActivity : FragmentActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var viewmodel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val movieId = intent.getIntExtra("id", 0)
        val repository = (application as MyApplication).tmdbRepo

        viewmodel = ViewModelProvider(this, DetailViewModelFactory(repository, movieId))
            .get(DetailViewModel::class.java)

        viewmodel.movieDetails.observe(this) {
            when (it) {
                is Response.Loading -> {

                }
                is Response.Error -> {

                }
                is Response.Success -> {
                    setData(it.data)
                }
            }
        }
    }

    private fun setData(response: DetailResponse?) {
        binding.title.text = response?.title ?: ""
        binding.originalTitle.text = response?.original_title ?: ""
        binding.slogan.text = response?.tagline ?: ""
        binding.description.text = response?.overview ?: ""
        binding.subtitle.text = getSubtitle(response)

        val path = "https://image.tmdb.org/t/p/w780" + (response?.backdrop_path ?: "")
        Glide.with(this)
            .load(path)
            .into(binding.imgBanner)
    }

    fun getSubtitle(response: DetailResponse?): String {
        val rating = if (response!!.adult) {
            "18+"
        } else {
            "12+"
        }

        val genres = response.genres.joinToString(
            prefix = " ",
            postfix = " ",
            separator = " • "
        ) { it.name }

        val hours: Int = response.runtime / 60
        val min: Int = response.runtime % 60

        return "$rating $genres $hours г $min хв"
    }
}