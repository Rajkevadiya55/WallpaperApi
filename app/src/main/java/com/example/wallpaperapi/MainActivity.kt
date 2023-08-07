package com.example.wallpaperapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"
    var data = ArrayList<PhotosItem>()
    lateinit var search: String
    lateinit var adapter: WallpaperAdapter
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbtn.setOnClickListener {


        }
        binding.imgbtn.setOnClickListener {
            search = binding.edtsearch.text.toString()

            data.clear()

            loadWallpaper(search, page)
        }

        binding.nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->


            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                binding.progress.visibility = View.VISIBLE
                loadWallpaper(search, page)
            }

        })
    }

    fun loadWallpaper(search: String, page1: Int) {
        var apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        apiInterface.getdata(
            search,
            page1,
            "AiE8mEHipmRX28hFBB1WfEyGAsQiQxNTovdKHZCVEf7pmeZSzHsq4PvM"
        ).enqueue(object : Callback<WallpaperModel> {
            override fun onResponse(
                call: Call<WallpaperModel>,
                response: Response<WallpaperModel>
            ) {

                data.addAll(response.body()?.photos as ArrayList<PhotosItem>)
                adapter = WallpaperAdapter(data)

                binding.recycle.layoutManager = GridLayoutManager(this@MainActivity, 3)
                binding.recycle.adapter = adapter

            }

            override fun onFailure(call: Call<WallpaperModel>, t: Throwable) {

            }
        })
    }
}