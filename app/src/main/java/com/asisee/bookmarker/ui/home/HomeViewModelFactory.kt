package com.asisee.bookmarker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asisee.bookmarker.database.BookmarkDao

class HomeViewModelFactory(private val bookmarkDao: BookmarkDao) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeViewModel(bookmarkDao) as T
}