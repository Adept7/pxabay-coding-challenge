package com.shevel.pixabaychallenge.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.shevel.pixabaychallenge.R
import com.shevel.pixabaychallenge.databinding.FragmentDetailBinding
import com.shevel.pixabaychallenge.model.ImageData
import com.shevel.pixabaychallenge.utils.tagsFormatted

class DetailFragment : Fragment() {

    companion object {
        const val KEY_IMAGE_DATA = "image_data"
    }

    private var binding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        arguments?.getParcelable<ImageData>(KEY_IMAGE_DATA)?.let { applyData(it) }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        menu.findItem(R.id.search).isVisible = false
    }

    private fun applyData(data: ImageData) {
        binding?.apply {
            Glide.with(root)
                .load(data.largeImageURL)
                .into(image)
            username.setText(data.user)
            username.isEnabled = false
            username.isActivated = true

            tags.setText(tagsFormatted(data.tags))
            tags.isEnabled = false
            tags.isActivated = true

            likes.setText(data.likes.toString())
            likes.isEnabled = false
            likes.isActivated = true

            downloads.setText(data.downloads.toString())
            downloads.isEnabled = false
            downloads.isActivated = true

            comments.setText(data.comments.toString())
            comments.isEnabled = false
            comments.isActivated = true
        }
    }

}