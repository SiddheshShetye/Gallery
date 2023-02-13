package com.siddroid.gallery.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.gallery.BR
import com.siddroid.gallery.GridItem
import com.siddroid.gallery.OnPhotoClickListener
import com.siddroid.gallery.R
import com.siddroid.gallery.databinding.ItemPhotoBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PhotoGridAdapter @Inject constructor(private val clickListener: OnPhotoClickListener): Adapter<PhotoViewHolder>() {
    private lateinit var binding: ItemPhotoBinding
    private val dataList: MutableList<GridItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_photo, parent, false)
        return PhotoViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val gridItem = dataList[position]
        holder.bindDetails(gridItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateList(photoList: List<GridItem>) {
        dataList.clear()
        dataList.addAll(photoList)
        notifyItemRangeInserted(0, dataList.size)
    }
}

class PhotoViewHolder(private val photoBinding: ItemPhotoBinding, private val clickHandler: OnPhotoClickListener): ViewHolder(photoBinding.root) {

    fun bindDetails(gridItem: GridItem) {
        Picasso.get()
            .load(gridItem.url)
            .resize(800,0)
            .onlyScaleDown()
            .centerInside()
            .into(photoBinding.imvThumb)
        photoBinding.setVariable(BR.index, adapterPosition)
        photoBinding.setVariable(BR.clickHandler, clickHandler)
        photoBinding.executePendingBindings()
    }
}