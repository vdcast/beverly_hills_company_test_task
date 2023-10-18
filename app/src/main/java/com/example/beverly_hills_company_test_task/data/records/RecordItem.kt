package com.example.beverly_hills_company_test_task.data.records

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val place: Int,
    val winner: String,
    val date: String,
    val time: String
)