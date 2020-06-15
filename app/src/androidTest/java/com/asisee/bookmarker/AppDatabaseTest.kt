package com.asisee.bookmarker

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.asisee.bookmarker.database.AppDatabase
import com.asisee.bookmarker.database.Bookmark
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun bookmarkDaoTest() = runBlocking {
        var bookmark1 = Bookmark(bookmarkId = 1L, title = null, url = "url")
        db.bookmarkDao.insert(bookmark1)
        val bookmark2 = db.bookmarkDao.get(1L)
        assertEquals(bookmark1, bookmark2)
        bookmark2.title = "title"
        db.bookmarkDao.update(bookmark2)
        bookmark1 = db.bookmarkDao.get(1L)
        assertEquals(bookmark1, bookmark2)
    }
}