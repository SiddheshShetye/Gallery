package com.siddroid.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.gallery.databinding.ItemPhotoBinding
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext


class PhotoGridAdapter(context: Context): Adapter<PhotoViewHolder>() {
    private val dataList: MutableList<GridItem> = mutableListOf()
    @ApplicationContext private lateinit var context: Context
    init {
        Picasso.get()
            .setIndicatorsEnabled(true)
        val p: Picasso = Picasso.Builder(context)
            .memoryCache(LruCache(24000))
            .build()
        p.setIndicatorsEnabled(true)
        p.isLoggingEnabled = true
        Picasso.setSingletonInstance(p)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindDetails(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateList(photoList: List<GridItem>) {
        dataList.clear()
        dataList.addAll(photoList)
        notifyDataSetChanged()
    }
}

class PhotoViewHolder(private val photoBinding: ItemPhotoBinding): ViewHolder(photoBinding.root) {

    fun bindDetails(gridItem: GridItem) {
        val rlp = photoBinding.imvThumb.layoutParams
        val ratio: Float = gridItem.height / (gridItem.width * 1F)
        rlp.height = (rlp.width * ratio).toInt()
        photoBinding.imvThumb.layoutParams = rlp
        photoBinding.imvThumb.ratio = gridItem.ratio

        Picasso.get()
                .load(gridItem.url)
                .into(photoBinding.imvThumb)
    }
}