package com.example.beverly_hills_company_test_task.data.records

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: RecordItem)

    @Update
    suspend fun update(record: RecordItem)

    @Delete
    suspend fun delete(record: RecordItem)

    @Query("SELECT * FROM records ORDER BY id ASC")
    fun getAllRecords(): Flow<List<RecordItem>>

    @Query("DELETE FROM records WHERE place = :place")
    suspend fun deleteRecordByPlace(place: Int)
    @Query("SELECT * FROM records ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord(): RecordItem?
}