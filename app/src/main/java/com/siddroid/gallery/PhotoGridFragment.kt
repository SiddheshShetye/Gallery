package com.siddroid.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.siddroid.gallery.adapters.PhotoGridAdapter
import com.siddroid.gallery.databinding.FragmentPhotoGridBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoGridBinding.inflate(layoutInflater)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.rvPhotos.layoutManager = manager
        binding.rvPhotos.setHasFixedSize(true)
        binding.rvPhotos.addItemDecoration(DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        binding.rvPhotos.adapter = adapter
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gridDataFlow.collect {
                    adapter.updateList(it.imageUrlList)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it && viewModel.gridDataFlow.value.imageUrlList.isNullOrEmpty()) {
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