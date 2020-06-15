package com.asisee.bookmarker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmark: Bookmark)
    @Update
    suspend fun update(bookmark: Bookmark)
    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("DELETE FROM bookmark")
    suspend fun clear()

    @Query("SELECT * FROM bookmark WHERE bookmarkId = :bookmarkId")
    suspend fun get(bookmarkId: Long): Bookmark

    @Query("SELECT * FROM bookmark ORDER BY bookmarkId DESC")
    fun getAllSync(): LiveData<List<Bookmark>>
}