package com.asisee.bookmarker.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.asisee.bookmarker.R
import com.asisee.bookmarker.database.AppDatabase
import com.asisee.bookmarker.database.Bookmark
import com.asisee.bookmarker.databinding.FragmentFormBinding

class FormFragment : Fragment() {
    private val viewModel: FormViewModel by viewModels {
        FormViewModelFactory(
            AppDatabase.getInstance(
                requireActivity().application
            ).bookmarkDao
        )
    }
    private val args: FormFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFormBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false)
        when (args.bookmarkId) {
            0L -> {
                binding.editButton.visibility = View.GONE
                binding.deleteButton.visibility = View.GONE
                if (args.sharedString == null)
                    binding.bookmark = Bookmark()
                else
                    binding.bookmark = Bookmark(url = args.sharedString)
            }
            else -> {
                binding.bookmark = viewModel.getBookmarkById(args.bookmarkId)
                binding.saveButton.visibility = View.GONE
            }
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(FormFragmentDirections.actionFormFragmentToNavHome())
                viewModel.navigationToHomeCompleted()
            }
        })
        return binding.root
    }

}