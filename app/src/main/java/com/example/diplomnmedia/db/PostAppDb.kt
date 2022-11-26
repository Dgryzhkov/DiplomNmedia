package com.example.diplomnmedia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplomnmedia.dao.*
import com.example.diplomnmedia.entity.PostEntity
import com.example.diplomnmedia.entity.PostRemoteKeyEntity

/**
 *@Author Dgryzhkov
 */
@Database(
    entities = [PostEntity::class, PostRemoteKeyEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    Converters::class, CoordinatesConverter::class,
    ConvertersListIds::class
)
abstract class PostAppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun postRemoteKeyDao(): PostRemoteKeyDao
}