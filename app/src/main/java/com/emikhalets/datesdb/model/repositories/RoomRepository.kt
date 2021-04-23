package com.emikhalets.datesdb.model.repositories

import com.emikhalets.datesdb.model.database.DatesDao
import com.emikhalets.datesdb.model.database.TypesDao
import com.emikhalets.datesdb.model.entities.AppResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoomRepository(
        private val datesDao: DatesDao,
        private val typesDao: TypesDao,
) : DatabaseRepository {

    override suspend fun getAllDates(): Flow<AppResult<List<DateItem>>> = flow {
        try {
            datesDao.getAll().run {
                if (this.isEmpty()) emit(AppResult.Error.EmptyData)
                else emit(AppResult.Success(this))
            }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun getDateById(id: Long): Flow<AppResult<DateItem>> = flow {
        try {
            datesDao.getItem(id).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun insertDate(dateItem: DateItem): Flow<AppResult<Long>> = flow {
        try {
            datesDao.insert(dateItem).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun updateDate(id: Long): Flow<AppResult<Nothing>> = flow {
        try {
            val dateItem = datesDao.getItem(id)
            datesDao.update(dateItem).run { emit(AppResult.Complete) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun deleteDate(id: Long): Flow<AppResult<Nothing>> = flow {
        try {
            val dateItem = datesDao.getItem(id)
            datesDao.delete(dateItem).run { emit(AppResult.Complete) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun getAllTypes(): Flow<AppResult<List<DateType>>> = flow {
        try {
            typesDao.getAll().run {
                if (this.isEmpty()) emit(AppResult.Error.EmptyData)
                else emit(AppResult.Success(this))
            }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun getTypeById(id: Long): Flow<AppResult<DateType>> = flow {
        try {
            typesDao.getItem(id).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun insertType(type: DateType): Flow<AppResult<Long>> = flow {
        try {
            typesDao.insert(type).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun insertTypes(list: List<DateType>): Flow<AppResult<Int>> = flow {
        try {
            typesDao.insertAll(list).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun updateType(type: DateType): Flow<AppResult<Int>> = flow {
        try {
            typesDao.update(type).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }

    override suspend fun deleteType(type: DateType): Flow<AppResult<Int>> = flow {
        try {
            typesDao.delete(type).run { emit(AppResult.Success(this)) }
        } catch (ex: Exception) {
            emit(AppResult.Error.DatabaseError(ex))
        }
    }
}