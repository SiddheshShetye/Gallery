package com.siddroid.gallery.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.gallery.BR
import com.siddroid.gallery.ui.model.GridItem
import com.siddroid.gallery.ui.OnPhotoClickListener
import com.siddroid.gallery.R
import com.siddroid.gallery.databinding.ItemPhotoBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
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
        val count = dataList.size
        dataList.clear()
        notifyItemRangeRemoved(0, count)
        dataList.addAll(photoList)
        notifyItemRangeInserted(0, dataList.size)
    }
}

class PhotoViewHolder(private val photoBinding: ItemPhotoBinding, private val clickHandler: OnPhotoClickListener): ViewHolder(photoBinding.root) {

    fun bindDetails(gridItem: GridItem) {
        photoBinding.progress.visibility = View.VISIBLE
        Picasso.get()
            .load(gridItem.url)
            .resize(1000,0)
            .onlyScaleDown()
            .centerInside()
            .into(photoBinding.imvThumb, object : Callback {
                override fun onSuccess() {
                    photoBinding.progress.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    photoBinding.progress.visibility = View.GONE
                }

            })
        photoBinding.setVariable(BR.index, adapterPosition)
        photoBinding.setVariable(BR.clickHandler, clickHandler)
        photoBinding.executePendingBindings()
    }
}