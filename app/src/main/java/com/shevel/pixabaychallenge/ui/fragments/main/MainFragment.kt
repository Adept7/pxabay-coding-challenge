package com.shevel.pixabaychallenge.ui.fragments.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.shevel.pixabaychallenge.PixabayChallengeApp
import com.shevel.pixabaychallenge.R
import com.shevel.pixabaychallenge.databinding.FragmentMainBinding
import com.shevel.pixabaychallenge.model.ImageData
import com.shevel.pixabaychallenge.ui.fragments.detail.DetailFragment

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val adapter by lazy { SearchResultRecyclerViewAdapter() { showDetailsAlert(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as PixabayChallengeApp).appComponent.inject(viewModel)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initView()
        subscribeData()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.imagesLiveData.value == null) {
            viewModel.getNewImages("fruits")
        }
    }

    override fun onStart() {
        super.onStart()
        subscribeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        menu.findItem(R.id.search).isVisible = true
    }

    private fun initView() {
        setHasOptionsMenu(true)
        binding?.list?.apply {
            val columnCount = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 3
                else -> 2
            }
            layoutManager = GridLayoutManager(context, columnCount)
            adapter = this@MainFragment.adapter
            setHasFixedSize(false)
        }
    }

    private fun subscribeData() {
        viewModel.imagesLiveData.observe(requireActivity(), ::updateImages)
        viewModel.isLoadingLiveData.observe(requireActivity(), ::updateLoading)
    }

    private fun updateImages(images: List<ImageData>) {
        adapter.updateData(images)
    }

    private fun updateLoading(isLoading: Boolean) {
        binding?.progress?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDetailsAlert(data: ImageData) {
        AlertDialog.Builder(requireActivity())
            .setMessage(R.string.alert_message)
            .setPositiveButton(R.string.label_ok) { _, _ -> openDetails(data) }
            .setNegativeButton(R.string.label_cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun openDetails(data: ImageData) {
        findNavController().navigate(
            R.id.action_mainFragment_to_detailFragment,
            bundleOf(DetailFragment.KEY_IMAGE_DATA to data)
        )
    }
}