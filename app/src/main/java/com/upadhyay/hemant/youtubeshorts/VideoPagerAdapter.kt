package com.upadhyay.hemant.youtubeshorts

import VideoData
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class VideoPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    private val videoData = listOf(
        VideoData("Forest Channel", "sample_video", "Forest Channel", "Short description 1"),
        VideoData("Scenery Channel 2", "sample_video2", "Scenery Channel 2", "Short description 2"),
        VideoData("Hello World", "sample_video", "Hello World", "Short description 3"),
        VideoData("Hey man!", "sample_video2", "channel_image_url_4", "Short description 4"),
        VideoData("Hello Guys", "sample_video", "channel_image_url_5", "Short description 5")
    )

    override fun getItemCount(): Int {
        return videoData.size
    }

    override fun createFragment(position: Int): Fragment {
        val video = videoData[position]
        return VideoPlayerFragment.newInstance(video, position)
    }
}