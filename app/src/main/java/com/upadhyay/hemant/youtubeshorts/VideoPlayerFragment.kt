package com.upadhyay.hemant.youtubeshorts

import VideoData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource

class VideoPlayerFragment : Fragment() {

    private lateinit var playerView: PlayerView
    private lateinit var channelImage: ImageView
    private lateinit var channelName: TextView
    private lateinit var videoDescription: TextView
    private lateinit var playPauseIcon: ImageView
    private lateinit var likeButton: ImageView

    private var isLiked: Boolean = false

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private var currentVideoPosition: Long = 0

    companion object {
        private const val ARG_VIDEO_DATA = "arg_video_data"
        private const val ARG_POSITION = "arg_position"

        fun newInstance(videoData: VideoData, position: Int): VideoPlayerFragment {
            val fragment = VideoPlayerFragment()
            val args = Bundle().apply {
                putParcelable(ARG_VIDEO_DATA, videoData)
                putInt(ARG_POSITION, position)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_video_player, container, false)

        playerView = view.findViewById(R.id.playerView)
        channelImage = view.findViewById(R.id.channelImage)
        channelName = view.findViewById(R.id.channelName)
        videoDescription = view.findViewById(R.id.videoDescription)
        likeButton = view.findViewById(R.id.likeButton)


        val playPauseOverlay: View = view.findViewById(R.id.clickableOverlay)
        playPauseIcon = view.findViewById(R.id.playPauseIcon)
        playPauseOverlay.setOnClickListener {
            if (simpleExoPlayer.isPlaying) {
                Log.d("TAG", "onCreateView: pause tap registered")
                playPauseIcon.visibility = View.VISIBLE
                playPauseIcon.setImageResource(R.drawable.baseline_play_circle)
                simpleExoPlayer.pause()
            } else {
                Log.d("TAG", "onCreateView: play tap registered")
                playPauseIcon.setImageResource(R.drawable.baseline_pause_circle)
                playPauseIcon.visibility = View.GONE
                simpleExoPlayer.play()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoData = arguments?.getParcelable<VideoData>(ARG_VIDEO_DATA)

        // Initialize ExoPlayer
        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).build()

        val rawResource = when (videoData?.videoUrl) {
            "sample_video" -> R.raw.video_sample
            "sample_video2" -> R.raw.video_sample2
            else -> R.raw.video_sample
        }

        val fileUri = RawResourceDataSource.buildRawResourceUri(rawResource)

        // Set media source
        val mediaItem = MediaItem.fromUri(fileUri)
        simpleExoPlayer.setMediaItem(mediaItem)

        simpleExoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL

        // Prepare the player
        simpleExoPlayer.prepare()
        simpleExoPlayer.playWhenReady = true

        // Attach player to the PlayerView
        playerView.player = simpleExoPlayer

        Log.d("TAG", "onViewCreated: ${videoData?.channelName}")

        channelName.text = videoData?.channelName ?: ""
        videoDescription.text = videoData?.videoDescription ?: ""

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // Check if this fragment is the currently selected one
                if (position == arguments?.getInt(ARG_POSITION, -1)) {
                    // Restart video from the beginning
                    simpleExoPlayer.seekTo(0)
                    simpleExoPlayer.play()
                } else {
                    // Pause the video when not in focus
                    simpleExoPlayer.pause()
                }
            }
        })


        likeButton.setOnClickListener{
            isLiked = toggleLikeState()

            if (isLiked) {
                likeButton.setImageResource(R.drawable.baseline_thumb_upvote_selected)
            } else {
                likeButton.setImageResource(R.drawable.baseline_thumb_upvote_not_selected)
            }
        }

    }

    private fun toggleLikeState(): Boolean{
        return !isLiked
    }

    override fun onPause() {
        super.onPause()

        currentVideoPosition = simpleExoPlayer.currentPosition
        simpleExoPlayer.pause()
    }

    override fun onResume() {
        super.onResume()

        simpleExoPlayer.seekTo(currentVideoPosition)
        simpleExoPlayer.play()
    }


    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

}