package com.example.hanwha_kimdahye.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hanwha_kimdahye.data.model.Docs

/*
* Docs 테이블의 데이터와 상호작용하는 데 사용하는 메소드 정의
* */
@Dao
interface BookmarkDao {
    @Insert
    fun insert(docs: Docs)

    @Query("DELETE FROM Docs WHERE uid = :bookmarkUid")
    fun delete(bookmarkUid: Long)

    @Query("SELECT count(*) FROM Docs WHERE uid = :bookmarkUid")
    fun initCheck(bookmarkUid: Long): Int

    @Query("SELECT * FROM Docs WHERE category = :category")
    fun select(category: String): List<Docs>
}
