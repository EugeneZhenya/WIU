package ua.dp.klio.wiu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HomeFragment : Fragment() {
    lateinit var txtTitle: TextView
    lateinit var txtSlogan: TextView
    lateinit var txtDescription: TextView

    lateinit var imgBanner: ImageView
    lateinit var listFragment: ListFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
    }

    fun init(view: View) {
        imgBanner = view.findViewById(R.id.img_banner)
        txtTitle = view.findViewById(R.id.title)
        txtSlogan = view.findViewById(R.id.slogan)
        txtDescription = view.findViewById(R.id.description)

        listFragment = ListFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()

        val gson = Gson()
        val inpStream : InputStream  = requireContext().assets.open("movies.json")
        val bufReader = BufferedReader(InputStreamReader(inpStream))
        val dataList : DataModel = gson.fromJson(bufReader, DataModel::class.java)

        listFragment.bindData(dataList)

        updateBanner(dataList)
    }

    fun updateBanner(dataList: DataModel) {
        txtTitle.text = dataList.result[0].details[0].title
        txtDescription.text = dataList.result[0].details[0].overview
        txtSlogan.text =dataList.result[0].details[0].tagline

        val url = "https://image.tmdb.org/t/p/w780" + dataList.result[0].details[0].backdrop_path
        Glide.with(this)
            .load(url)
            .into(imgBanner)
    }
}