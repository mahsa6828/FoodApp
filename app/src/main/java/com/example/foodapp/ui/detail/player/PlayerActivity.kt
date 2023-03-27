package com.example.foodapp.ui.detail.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityPlayerBinding
import com.example.foodapp.utils.VIDEO_ID
import com.example.foodapp.utils.YOUTUBE_API_KEY
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
class PlayerActivity : YouTubeBaseActivity() {
    private var _binding : ActivityPlayerBinding? = null
    private val binding get() = _binding
    private var videoId=""
    private lateinit var player: YouTubePlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayerBinding.inflate(layoutInflater)
        //Full screen
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
        setContentView(binding?.root)
        //get data
        videoId = intent.getStringExtra(VIDEO_ID).toString()
        binding?.apply {
            val listener = object : YouTubePlayer.OnInitializedListener{
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider,
                    p1: YouTubePlayer,
                    p2: Boolean
                ) {
                    player= p1
                    player.loadVideo(videoId)
                    player.play()
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider,
                    p1: YouTubeInitializationResult?
                ) {

                }

            }
            videPlayer.initialize(YOUTUBE_API_KEY,listener)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}