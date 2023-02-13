package com.siddroid.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.siddroid.gallery.adapters.PhotoDetailsAdapter
import com.siddroid.gallery.databinding.FragmentPhotoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailsBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    @Inject lateinit var adapter: PhotoDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailsBinding.inflate(layoutInflater)
        binding.vpPhotoDetails.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        viewModel.getDetails()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailsDataStateFlow.collect {
                    adapter.updateData(it.detailsList)
                    binding.vpPhotoDetails.setCurrentItem(arguments?.getInt("photo_index") ?: 0, false)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { }
            }
        }
    }

    companion object {

        fun newInstance(index: Int): PhotoDetailsFragment {
            val data = Bundle().apply {
                putInt("photo_index", index)
            }
            val frag = PhotoDetailsFragment()
            frag.arguments = data
            return frag
        }
    }
}