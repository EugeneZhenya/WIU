package ua.dp.klio.wiu.player

import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackSeekDataProvider

class MyVideoFragment : VideoSupportFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerGlue = PlaybackTransportControlGlue(activity, MediaPlayerAdapter(activity))

        playerGlue.host = VideoSupportFragmentGlueHost(this)
        playerGlue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
            override fun onPreparedStateChanged(glue: PlaybackGlue?) {
                if (glue?.isPrepared == true) {
                    playerGlue.seekProvider = PlaybackSeekDataProvider()
                    playerGlue.play()
                }
            }
        })

        playerGlue.subtitle = "Короткий опис для відео"
        playerGlue.title = "Назва відео"
        val uriPath = "https://fast.wistia.com/embed/medias/vtx1pnpcvj.m3u8"
        playerGlue.playerAdapter.setDataSource(Uri.parse(uriPath))
    }
}