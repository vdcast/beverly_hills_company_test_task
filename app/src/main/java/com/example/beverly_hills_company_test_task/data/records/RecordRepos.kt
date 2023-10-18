package com.example.beverly_hills_company_test_task.data.records

import kotlinx.coroutines.flow.Flow

interface RecordRepos {
    suspend fun insert(record: RecordItem)
    suspend fun update(record: RecordItem)
    suspend fun delete(record: RecordItem)
    fun getAllRecords(): Flow<List<RecordItem>>
    suspend fun deleteRecordByPlace(place: Int)
    suspend fun getLastRecord(): RecordItem?
}

class RecordReposImpl(private val recordDao: RecordDao) : RecordRepos {
    override suspend fun insert(record: RecordItem) = recordDao.insert(record)

    override suspend fun update(record: RecordItem) = recordDao.update(record)

    override suspend fun delete(record: RecordItem) = recordDao.delete(record)

    override fun getAllRecords(): Flow<List<RecordItem>> = recordDao.getAllRecords()
    override suspend fun deleteRecordByPlace(place: Int) = recordDao.deleteRecordByPlace(place)
    override suspend fun getLastRecord(): RecordItem?  = recordDao.getLastRecord()

}