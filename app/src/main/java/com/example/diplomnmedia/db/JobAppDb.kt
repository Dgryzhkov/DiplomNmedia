package com.example.diplomnmedia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplomnmedia.dao.Converters
import com.example.diplomnmedia.dao.JobDao
import com.example.diplomnmedia.entity.JobEntity

/**
 *@Author Dgryzhkov
 */
@Database(entities = [JobEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JobAppDb : RoomDatabase() {
    abstract fun jobDao(): JobDao
}