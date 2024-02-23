package com.upadhyay.hemant.youtubeshorts

import VideoData
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class VideoPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    private val videoData = listOf(
        VideoData("Video 1", "sample_video", "channel_image_url_1", "Short description 1"),
        VideoData("Video 2", "sample_video", "channel_image_url_2", "Short description 2"),
        VideoData("Video 3", "sample_video", "channel_image_url_3", "Short description 3"),
        VideoData("Video 4", "sample_video", "channel_image_url_4", "Short description 4"),
        VideoData("Video 5", "sample_video", "channel_image_url_5", "Short description 5")
    )

    override fun getItemCount(): Int {
        return videoData.size
    }

    override fun createFragment(position: Int): Fragment {
        val video = videoData[position]
        return VideoPlayerFragment()
    }
}