package com.example.diplomnmedia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplomnmedia.dao.*
import com.example.diplomnmedia.entity.EventEntity
import com.example.diplomnmedia.entity.EventRemoteKeyEntity

/**
 *@Author Dgryzhkov
 */
@Database(
    entities = [EventEntity::class, EventRemoteKeyEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    Converters::class, CoordinatesConverter::class, EventTypeConverters::class,
    ConvertersListIds::class
)
abstract class EventAppDb : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun eventRemoteKeyDao(): EventRemoteKeyDao
}