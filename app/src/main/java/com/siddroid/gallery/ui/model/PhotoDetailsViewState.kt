package com.siddroid.gallery.ui.model

data class PhotoDetailsViewState(val detailsList: List<PhotoDetailItem>)

data class PhotoDetailItem(val photoUrl: String = "", val photoTitle: String = "", val photoDescription: String = "")