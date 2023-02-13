package com.siddroid.gallery


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.gallery.databinding.ItemPhotoBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PhotoGridAdapter @Inject constructor(val clickListener: OnPhotoClickListener): Adapter<PhotoViewHolder>() {
    private lateinit var binding: ItemPhotoBinding
    private val dataList: MutableList<GridItem> = mutableListOf()
//    @ActivityContext lateinit var clickHandler: OnPhotoClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        binding = DataBindingUtil.inflate<ItemPhotoBinding>(LayoutInflater.from(parent.context), R.layout.item_photo, parent, false)
        return PhotoViewHolder(binding, clickListener)
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

class PhotoViewHolder(val photoBinding: ItemPhotoBinding, private val clickHandler: OnPhotoClickListener): ViewHolder(photoBinding.root) {

    fun bindDetails(gridItem: GridItem) {
        Picasso.get()
                .load(gridItem.url)
            .placeholder(R.drawable.ic_launcher_background)
                .into(photoBinding.imvThumb)
        photoBinding.setVariable(BR.index, adapterPosition)
        if (clickHandler is OnPhotoClickListener) {
            photoBinding.setVariable(BR.clickHandler, clickHandler)
        }
        photoBinding.executePendingBindings()

    }
}