package ua.dp.klio.wiu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updatePadding
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import ua.dp.klio.wiu.utils.Common.Companion.getWidthInPercent
import ua.dp.klio.wiu.utils.Common.Companion.getHeightInPercent

class ItemPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_view, parent, false)

        val params = view.layoutParams
        params.width = getWidthInPercent(parent!!.context, 12)
        params.height = getHeightInPercent(parent!!.context, 32)
        view.updatePadding(right = 5, left = 5)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val content = item as? DataModel.Result.Detail

        var url = ""
        if (content!!.isKlio) {
            val view = viewHolder?.view
            val params = view!!.layoutParams
            params.width = getWidthInPercent(view!!.context, 24)
            params.height = getHeightInPercent(view!!.context, 32)

            url = "https://archive.org/download/klio_partitions" + content?.poster_path
        } else {
            url = "https://image.tmdb.org/t/p/w500" + content?.poster_path
        }

        val imageview = viewHolder?.view?.findViewById<ImageView>(R.id.poster_image)

        Glide.with(viewHolder?.view?.context!!)
            .load(url)
            .into(imageview!!)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}