package com.upadhyay.hemant.youtubeshorts

import VideoData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource

class VideoPlayerFragment : Fragment() {

    private lateinit var playerView: PlayerView
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var dataChannelName: String
    private lateinit var channelImage: String
    private lateinit var shortDescription: String

    companion object {
        private const val ARG_VIDEO_DATA = "arg_video_data"

        fun newInstance(videoData: VideoData): VideoPlayerFragment {
            val fragment = VideoPlayerFragment()
            val args = Bundle().apply {
                putParcelable(ARG_VIDEO_DATA, videoData)
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
//        playerView.useController = false

        playerView.setOnClickListener {
            if (simpleExoPlayer.isPlaying) {
                simpleExoPlayer.pause()
            } else {
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

//        val rawResource = when (videoData?.videoUrl) {
//            "sample_video" -> R.raw.video_sample
//            else -> R.raw.video_sample
//        }

        val fileUri = RawResourceDataSource.buildRawResourceUri(R.raw.video_sample)

        // Set media source
        val mediaItem = MediaItem.fromUri(fileUri)
        simpleExoPlayer.setMediaItem(mediaItem)

        // Prepare the player
        simpleExoPlayer.prepare()
        simpleExoPlayer.playWhenReady = true

        // Attach player to the PlayerView
        playerView.player = simpleExoPlayer

        dataChannelName = videoData?.channelName ?: ""
        channelImage = videoData?.channelImage ?: ""
        shortDescription = videoData?.videoDescription ?: ""

//        updateUI()

    }

//    private fun updateUI() {
//        val channelName: TextView = requireActivity().findViewById(R.id.channelName)
//        val videoDescription: TextView = requireActivity().findViewById(R.id.videoDescription)
//
//        channelName.text = dataChannelName
//        videoDescription.text = shortDescription
//    }


    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

}