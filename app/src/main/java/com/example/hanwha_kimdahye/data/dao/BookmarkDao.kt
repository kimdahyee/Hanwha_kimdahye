package com.example.hanwha_kimdahye.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hanwha_kimdahye.data.model.Bookmark

@Dao
interface BookmarkDao {
    @Insert
    fun insert(bookmark: Bookmark)

    @Query("DELETE FROM Bookmark WHERE uid = :bookmarkUid")
    fun delete(bookmarkUid: Long)

    @Query("SELECT count(*) FROM Bookmark WHERE uid = :bookmarkUid")
    fun initCheck(bookmarkUid: Long): Int
}
