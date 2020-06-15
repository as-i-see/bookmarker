package com.asisee.bookmarker.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asisee.bookmarker.database.BookmarkDao

class FormViewModelFactory(private val bookmarkDao: BookmarkDao) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = FormViewModel(bookmarkDao) as T
}