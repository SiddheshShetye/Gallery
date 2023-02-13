package com.siddroid.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.siddroid.gallery.databinding.FragmentPhotoGridBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotoGridFragment : Fragment(), OnPhotoClickListener {

    private lateinit var binding: FragmentPhotoGridBinding
    private val viewModel: MainActivityViewModel by viewModels()
    @Inject lateinit var adapter: PhotoGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoGridBinding.inflate(layoutInflater)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rvPhotos.layoutManager = manager
        binding.rvPhotos.setHasFixedSize(true)
        binding.rvPhotos.setItemViewCacheSize(10);
        binding.rvPhotos.addItemDecoration(DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        binding.rvPhotos.adapter = adapter
        viewModel.getImages()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gridDataFlow.collect {
                    adapter.updateList(it.imageUrlList)
                }
            }
        }

    }

    override fun onPhotoClicked(index: Int) {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            addToBackStack("photo_details")
            arguments = Bundle().apply {
                putInt("photo_index", index)
            }
            replace<PhotoDetailsFragment>(R.id.container)
        }
    }
}