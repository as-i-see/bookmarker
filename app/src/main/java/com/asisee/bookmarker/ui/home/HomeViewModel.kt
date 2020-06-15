package com.asisee.bookmarker.ui.home

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asisee.bookmarker.database.BookmarkDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val bookmarkDao: BookmarkDao) : ViewModel() {

    val bookmarks = bookmarkDao.getAllSync()

    fun deleteAll() {
        viewModelScope.launch {
            bookmarkDao.clear()
        }
    }

    fun fillIntent(intent: Intent, bookmarkId: Long) = runBlocking {
        intent.data = Uri.parse(bookmarkDao.get(bookmarkId).url)
    }
}