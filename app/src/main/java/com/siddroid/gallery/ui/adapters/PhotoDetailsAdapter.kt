package com.siddroid.gallery.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.gallery.ui.model.PhotoDetailItem
import com.siddroid.gallery.R
import com.siddroid.gallery.databinding.ItemPhotoDetailsBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PhotoDetailsAdapter: Adapter<PhotoDetailViewHolder>() {

    private val photoDetailsList = mutableListOf<PhotoDetailItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDetailViewHolder {
        val binding = DataBindingUtil.inflate<ItemPhotoDetailsBinding>(LayoutInflater.from(parent.context), R.layout.item_photo_details, parent, false)
        return PhotoDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoDetailViewHolder, position: Int) {
        holder.bindData(photoDetailsList[position])
    }

    override fun getItemCount(): Int {
        return photoDetailsList.size
    }

    fun updateData(detailsList: List<PhotoDetailItem>) {
        photoDetailsList.clear()
        photoDetailsList.addAll(detailsList)
    }
}

class PhotoDetailViewHolder(private val binding: ItemPhotoDetailsBinding): ViewHolder(binding.root) {
    fun bindData(photoDetails: PhotoDetailItem) {
        binding.setVariable(BR.photoDetails, photoDetails)
        binding.progress.visibility = View.VISIBLE
        Picasso.get()
            .load(photoDetails.photoUrl)
            .resize(800,0)
            .centerInside()
            .into(binding.imvPhoto, object: Callback {
                override fun onSuccess() {
                    binding.progress.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    binding.progress.visibility = View.GONE
                }

            })
        binding.executePendingBindings()
    }
}