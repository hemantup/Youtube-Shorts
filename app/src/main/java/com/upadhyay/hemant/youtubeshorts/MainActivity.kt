package com.upadhyay.hemant.youtubeshorts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewPager: ViewPager2 = findViewById(R.id.viewPager2)
        val adapter = VideoPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

    }
}