package com.example.wallpaperapi

import android.app.Dialog
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaperapi.databinding.DailogItemBinding

class WallpaperAdapter(data: ArrayList<PhotosItem>) :
    RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>() {

    var data = data
    lateinit var context: Context

    class WallpaperHolder(itemView: View) : ViewHolder(itemView) {

        var img = itemView.findViewById<ImageView>(R.id.img)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WallpaperAdapter.WallpaperHolder {
        context = parent.context
        return WallpaperHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WallpaperAdapter.WallpaperHolder, position: Int) {
        Glide.with(holder.itemView.context).load(data.get(position).src?.portrait).into(holder.img)


        holder.itemView.setOnClickListener {
            var dailog = Dialog (holder.itemView.context)
            dailog.setContentView(R.layout.dailog_item)

            var img=dailog.findViewById<ImageView>(R.id.img)
            var btn=dailog.findViewById<Button>(R.id.btndialog)

            Glide.with(holder.itemView.context).load(data.get(position).src?.portrait).into(img)

            btn.setOnClickListener {
                Glide.with(context)
                    .asBitmap().load(data[position].src?.portrait)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            try {
                                wallpaperManager.setBitmap(resource)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return false
                        }
                    }).submit()
            }
            dailog.show()
        }
    }
}