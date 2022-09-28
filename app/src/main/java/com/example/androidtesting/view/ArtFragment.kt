package com.example.androidtesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesting.R
import com.example.androidtesting.adapter.ArtAdapter
import com.example.androidtesting.databinding.FragmentArtsBinding
import com.example.androidtesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(val artAdapter: ArtAdapter): Fragment(R.layout.fragment_arts) {

    private var fragmentBinding : FragmentArtsBinding?= null
    lateinit var viewModel: ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerViewArt.adapter = artAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment2())
        }

    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner) {
            artAdapter.arts = it
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}