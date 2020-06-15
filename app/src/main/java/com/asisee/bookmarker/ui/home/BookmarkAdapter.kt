package com.asisee.bookmarker.ui.home

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asisee.bookmarker.database.Bookmark
import com.asisee.bookmarker.databinding.ListItemBookmarkBinding

class BookmarkAdapter(val clickListener: BookmarkListener, val editListener: BookmarkListener) :
    ListAdapter<Bookmark, BookmarkAdapter.ViewHolder>(BookmarkDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, editListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: BookmarkListener, editListener: BookmarkListener, item: Bookmark) {
            binding.bookmark = item
            binding.clickListener = clickListener
            binding.editListener = editListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBookmarkBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BookmarkListener(val clickListener: (bookmarkId: Long) -> Unit) {
    fun onClick(bookmark: Bookmark) = clickListener(bookmark.bookmarkId)
}

class BookmarkDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
    override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
        return oldItem.bookmarkId == newItem.bookmarkId
    }

    override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
        return oldItem == newItem
    }

}

class LineDivider(private val mDivider: Drawable) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val top = child.bottom + child.marginBottom
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}