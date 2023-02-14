package com.siddroid.gallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.siddroid.gallery.ui.OnPhotoClickListener
import com.siddroid.gallery.R
import com.siddroid.gallery.ui.adapters.PhotoGridAdapter
import com.siddroid.gallery.databinding.FragmentPhotoGridBinding
import com.siddroid.gallery.ui.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PhotoGridFragment : Fragment(), OnPhotoClickListener {

    private lateinit var binding: FragmentPhotoGridBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    @Inject lateinit var adapter: PhotoGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoGridBinding.inflate(layoutInflater)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.rvPhotos.layoutManager = manager
        binding.rvPhotos.setHasFixedSize(true)
        val dividerVerticle = DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider)
        drawable?.let {
            dividerVerticle.setDrawable(drawable)
        }
        binding.rvPhotos.addItemDecoration(dividerVerticle)
        val dividerHorizontal = DividerItemDecoration(context, StaggeredGridLayoutManager.HORIZONTAL)
        drawable?.let {
            dividerHorizontal.setDrawable(drawable)
        }
        binding.rvPhotos.addItemDecoration(dividerHorizontal)
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPhotos.adapter = adapter
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gridDataFlow.collect {
                    withContext(Dispatchers.Main) {
                        adapter.updateList(it.imageUrlList)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it && viewModel.gridDataFlow.value.imageUrlList.isEmpty()) {
                        viewModel.getImages()
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Not Connected to Internet", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }

    }

    override fun onPhotoClicked(index: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("photo_details")
            replace(R.id.container, PhotoDetailsFragment.newInstance(index))
        }
    }
}