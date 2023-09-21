package com.example.nasaimageofday

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nasaimageofday.api.RetrofitInstance
import com.example.nasaimageofday.databinding.ActivityMainBinding
import com.example.nasaimageofday.databinding.FragmentImageLayoutBinding
import com.example.nasaimageofday.db.ImageDatabase
import com.example.nasaimageofday.model.ApiImageResponse
import com.example.nasaimageofday.repository.ImageRepository

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var imageDatabase: ImageDatabase
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Adding ImageFragment as the default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ImageFragment())
                .commit()
        }

        imageDatabase = ImageDatabase.invoke(this)

        // Initialize ImageRepository with RetrofitInstance
        val imageRepository = ImageRepository(imageDatabase)

        // Initialize MainViewModel with the ImageRepository
        viewModel = ViewModelProvider(this, MainViewModelFactory(imageRepository))
            .get(MainViewModel::class.java)

        //Adding observers
        viewModel.imageModel.observe(this) { apiModel ->
            binding.apply {
                dataAbsent = false
            }
            val imageFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as ImageFragment?
            imageFragment?.loadData(apiModel)
        }

        viewModel.loading.observe(this) {
            it?.let {
                binding.loading = it
            }
        }

        viewModel.errorResponse.observe(this) {
            binding.apply {
                dataAbsent = true
            }

            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }

        // Call the getImageOfDay function to fetch data
        viewModel.getImageOfDay()
    }
}

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