package com.siddroid.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.gallery.databinding.ItemPhotoBinding
import com.squareup.picasso.Picasso


class PhotoGridAdapter(): Adapter<PhotoViewHolder>() {
    private val dataList: MutableList<GridItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val gridItem = dataList[position]
        val rlp = holder.photoBinding.imvThumb.layoutParams
        rlp?.let {
            val ratio: Float = gridItem.height / (gridItem.width * 1F)
            it.height = (it.width * ratio).toInt()
            holder.photoBinding.imvThumb.layoutParams = it
            holder.photoBinding.imvThumb.ratio = gridItem.ratio
        }
        holder.bindDetails(gridItem)
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

class PhotoViewHolder(val photoBinding: ItemPhotoBinding): ViewHolder(photoBinding.root) {

    fun bindDetails(gridItem: GridItem) {
        Picasso.get()
                .load(gridItem.url)
            .placeholder(R.drawable.ic_launcher_background)
                .into(photoBinding.imvThumb)
    }
}