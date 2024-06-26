package ua.dp.klio.wiu

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ua.dp.klio.wiu.api.ApiService
import ua.dp.klio.wiu.api.Response
import ua.dp.klio.wiu.api.RetrofitHelper
import ua.dp.klio.wiu.api.TmdbRepo
import ua.dp.klio.wiu.databinding.ActivityDetailBinding
import ua.dp.klio.wiu.model.DetailResponse
import ua.dp.klio.wiu.player.PlaybackActivity
import ua.dp.klio.wiu.utils.Common
import ua.dp.klio.wiu.utils.Common.Companion.isEllipsized
import ua.dp.klio.wiu.viewmodel.DetailViewModel
import ua.dp.klio.wiu.viewmodel.DetailViewModelFactory

class DetailActivity : FragmentActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var viewmodel: DetailViewModel
    val castFragment = ListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        addFragment(castFragment)

        val movieId = intent.getIntExtra("id", 0)
        // val repository = (application as MyApplication).tmdbRepo

        val service = RetrofitHelper.getInstance().create(ApiService::class.java)
        val repository = TmdbRepo(service)

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

        viewmodel.castDetails.observe(this) {
            when (it) {
                is Response.Loading -> {

                }
                is Response.Error -> {

                }
                is Response.Success -> {
                    if (!it.data?.cast.isNullOrEmpty()) {
                        castFragment.bindCastData(it.data?.cast!!)
                    }
                }
            }
        }

        binding.addToMylist.setOnKeyListener { view, keyCode, keyEvent ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                        castFragment.requestFocus()
                    }
                }
            }

            false
        }

        binding.moreLikeThis.setOnKeyListener { view, keyCode, keyEvent ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                        castFragment.requestFocus()
                    }
                }
            }

            false
        }

        binding.play.setOnKeyListener { view, keyCode, keyEvent ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                        castFragment.requestFocus()
                    }
                }
            }

            false
        }
        
        binding.play.setOnKeyListener { v, keyCode, event ->
            val intent = Intent(this, PlaybackActivity::class.java)
            startActivity(intent)
            false
        }
    }

    private fun addFragment(castFragment: ListFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, castFragment)
        transaction.commit()
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

        binding.description.isEllipsized { isEllipsized ->
            binding.showMore.visibility = if (isEllipsized) View.VISIBLE else View.GONE

            binding.showMore.setOnClickListener {

                Common.descriptionDialog(
                    this,
                    response?.title,
                    getSubtitle(response),
                    response?.overview.toString()
                )

            }
        }
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