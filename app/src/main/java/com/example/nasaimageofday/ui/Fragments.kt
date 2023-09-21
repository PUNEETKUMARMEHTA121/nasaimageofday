package com.example.nasaimageofday.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nasaimageofday.R
import com.example.nasaimageofday.databinding.FragmentImageLayoutBinding
import com.example.nasaimageofday.model.ApiImageResponse

class ImageFragment : Fragment(R.layout.fragment_image_layout) {
    private lateinit var binding: FragmentImageLayoutBinding
    private lateinit var apiResponse: ApiImageResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the data binding
        binding = FragmentImageLayoutBinding.bind(view)

        // Set an OnClickListener for the Play Button
        binding.button.setOnClickListener {
            // Show the VideoView and play the video
            binding.videoView.start()
        }
    }

    fun loadData(apiImageResponse: ApiImageResponse) {
        apiResponse = apiImageResponse
        // Setting data to the binding variables
        binding.apply {
            titleString = apiImageResponse.title.toString()
            dateString = apiImageResponse.date.toString()
            descriptionString = apiImageResponse.explanation.toString()
            if (apiImageResponse.mediaType == "video") {
                isVideoView = true
                binding.videoView.setVideoPath(apiResponse.url)
            } else {
                Glide.with(requireContext()).load(apiImageResponse.url).into(binding.image)
            }
        }
    }
}