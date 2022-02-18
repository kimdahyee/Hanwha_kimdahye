package com.example.hanwha_kimdahye.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hanwha_kimdahye.data.dao.BookmarkDao
import com.example.hanwha_kimdahye.data.model.Bookmark

/*
* 데이터베이스를 생성하고 관리하는 데이터베이스 객체를 만들기 위한 추상클래스
* */
@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private var instance: BookmarkDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BookmarkDatabase? {
            if (instance == null) {
                synchronized(BookmarkDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkDatabase::class.java,
                        "bookmark-database"
                    ).build()
                }
            }
            return instance
        }
    }
}
