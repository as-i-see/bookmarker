package com.asisee.bookmarker.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asisee.bookmarker.database.Bookmark
import com.asisee.bookmarker.database.BookmarkDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FormViewModel(private val bookmarkDao: BookmarkDao) : ViewModel() {
    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    fun insert(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkDao.insert(bookmark)
        }
        _navigateToHome.value = true

    }

    fun update(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkDao.update(bookmark)
        }
        _navigateToHome.value = true
    }

    fun delete(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkDao.delete(bookmark)
        }
        _navigateToHome.value = true
    }

    fun getBookmarkById(bookmarkId: Long): Bookmark = runBlocking {
        bookmarkDao.get(bookmarkId)
    }

    fun navigationToHomeCompleted() {
        _navigateToHome.value = false
    }
}