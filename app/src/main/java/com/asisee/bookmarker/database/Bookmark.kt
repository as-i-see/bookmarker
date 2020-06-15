package com.asisee.bookmarker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var bookmarkId: Long = 0L,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "url") var url: String? = null
)