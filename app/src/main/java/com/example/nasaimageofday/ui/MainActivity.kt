package com.example.nasaimageofday.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.nasaimageofday.R
import com.example.nasaimageofday.databinding.ActivityMainBinding
import com.example.nasaimageofday.db.ImageDatabase
import com.example.nasaimageofday.repository.ImageRepository
import com.example.nasaimageofday.viewModel.MainViewModel
import com.example.nasaimageofday.viewModel.MainViewModelFactory

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

