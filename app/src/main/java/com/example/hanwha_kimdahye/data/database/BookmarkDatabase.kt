package com.example.hanwha_kimdahye.data.database

import android.content.Context
import androidx.room.* // ktlint-disable no-wildcard-imports
import com.example.hanwha_kimdahye.data.dao.BookmarkDao
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.util.Converters

/*
* 데이터베이스를 생성하고 관리하는 데이터베이스 객체를 만들기 위한 추상클래스
* */
@Database(entities = [Docs::class], version = 2)
@TypeConverters(Converters::class)
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
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }
    }
}
