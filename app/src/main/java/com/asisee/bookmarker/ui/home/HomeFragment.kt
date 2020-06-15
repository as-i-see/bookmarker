package com.asisee.bookmarker.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.asisee.bookmarker.R
import com.asisee.bookmarker.database.AppDatabase
import com.asisee.bookmarker.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            AppDatabase.getInstance(
                requireActivity().application
            ).bookmarkDao
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        binding.addButton.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToFormFragment())
        }
        binding.lifecycleOwner = this

        val adapter = BookmarkAdapter(BookmarkListener { bookmarkId ->
            run {
                val intent = Intent(Intent.ACTION_VIEW)
                viewModel.fillIntent(intent, bookmarkId)
                val chooser =
                    Intent.createChooser(intent, resources.getText(R.string.chooser_title))
                startActivity(chooser)
            }
        }, BookmarkListener { bookmarkId ->
            run {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionNavHomeToFormFragment(bookmarkId))
            }
        })
        binding.bookmarkList.addItemDecoration(
            LineDivider(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.line_divider
                )!!
            )
        )
        binding.bookmarkList.adapter = adapter
        viewModel.bookmarks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAll()
                showOnClearSnackbar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showOnClearSnackbar() {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            getString(R.string.message_cleared),
            Snackbar.LENGTH_SHORT
        ).show()
    }


}