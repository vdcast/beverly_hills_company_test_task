package com.example.beverly_hills_company_test_task.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beverly_hills_company_test_task.data.records.RecordDao
import com.example.beverly_hills_company_test_task.data.records.RecordItem

@Database(entities = [RecordItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}