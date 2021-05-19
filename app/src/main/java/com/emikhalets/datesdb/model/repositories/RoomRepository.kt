package com.emikhalets.datesdb.model.repositories

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.ListResult
import com.emikhalets.datesdb.model.SingleResult
import com.emikhalets.datesdb.model.database.DatesDao
import com.emikhalets.datesdb.model.database.GroupsDao
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.Group

class RoomRepository(
    private val datesDao: DatesDao,
    private val groupsDao: GroupsDao,
) : DatabaseRepository {

    override suspend fun getAllDates(): ListResult<List<DateItem>> {
        return try {
            val result = datesDao.getAll()
            if (result.isEmpty()) ListResult.EmptyList
            else ListResult.Success(result)
        } catch (ex: Exception) {
            ListResult.Error(ex)
        }
    }

    override suspend fun getDateById(id: Long): SingleResult<DateItem> {
        return try {
            val result = datesDao.getItem(id)
            SingleResult.Success(result)
        } catch (ex: Exception) {
            SingleResult.Error(ex)
        }
    }

    override suspend fun insertDate(dateItem: DateItem): CompleteResult<Nothing> {
        return try {
            datesDao.insert(dateItem)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun updateDate(dateItem: DateItem): CompleteResult<Nothing> {
        return try {
            datesDao.update(dateItem)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun deleteDate(dateItem: DateItem): CompleteResult<Nothing> {
        return try {
            datesDao.delete(dateItem)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun getAllGroups(): ListResult<List<Group>> {
        return try {
            val result = groupsDao.getAll()
            if (result.isEmpty()) ListResult.EmptyList
            else ListResult.Success(result)
        } catch (ex: Exception) {
            ListResult.Error(ex)
        }
    }

    override suspend fun insertGroups(list: List<Group>): CompleteResult<Nothing> {
        return try {
            groupsDao.insertAll(list)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun getGroupByName(name: String): SingleResult<Group> {
        return try {
            val isExist = groupsDao.isExist(name)
            return if (isExist) {
                val groupName = groupsDao.getItem(name)
                SingleResult.Success(groupName)
            } else {
                // TODO: handle if group not exist
                SingleResult.Error()
            }
        } catch (ex: Exception) {
            SingleResult.Error(ex)
        }
    }

    override suspend fun insertGroup(group: Group): CompleteResult<Nothing> {
        return try {
            val isExist = groupsDao.isExist(group.name)
            return if (isExist) {
                // TODO: handle if group already exist
                CompleteResult.Error()
            } else {
                groupsDao.insert(group)
                CompleteResult.Complete
            }
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun updateGroup(group: Group): CompleteResult<Nothing> {
        return try {
            groupsDao.update(group)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun deleteGroup(group: Group): CompleteResult<Nothing> {
        return try {
            groupsDao.delete(group)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }
}