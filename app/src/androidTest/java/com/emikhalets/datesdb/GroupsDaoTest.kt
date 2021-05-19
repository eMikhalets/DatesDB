package com.emikhalets.datesdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.database.GroupsDao
import com.emikhalets.datesdb.model.entities.Group
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GroupsDaoTest {

    private lateinit var groupsDao: GroupsDao
    private lateinit var db: AppDatabase

    private val type1 = Group("type1")
    private val type2 = Group("type2")
    private val type3 = Group("type3")


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        groupsDao = db.groupsDao

        runBlocking {
            groupsDao.insert(type1)
            groupsDao.insert(type2)
            groupsDao.insert(type3)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testGetType() = runBlocking {
        assertThat(groupsDao.getType("type1").name, equalTo(type1.name))
        assertThat(groupsDao.getType("type2").name, equalTo(type2.name))
        assertThat(groupsDao.getType("type3").name, equalTo(type3.name))
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllTypes() = runBlocking {
        val list = groupsDao.getAllTypes()

        assertThat(list.size, equalTo(3))
        assertThat(list[0].name, equalTo(type1.name))
        assertThat(list[1].name, equalTo(type2.name))
        assertThat(list[2].name, equalTo(type3.name))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndDeleteType() = runBlocking {
        val type = Group("type4")
        groupsDao.insert(type)

        assertThat(groupsDao.getAllTypes().last().name, equalTo(type.name))

        groupsDao.delete(groupsDao.getAllTypes()[3])

        assertThat(groupsDao.getAllTypes().size, equalTo(3))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndUpdateType() = runBlocking {
        val type = groupsDao.getAllTypes()[0]
        val oldName = type.name
        val newName = "name5"
        type.name = newName
        groupsDao.update(type)

        assertThat(groupsDao.getAllTypes()[0].name, equalTo(newName))

        type.name = oldName
        groupsDao.update(type)

        assertThat(groupsDao.getAllTypes()[0].name, equalTo(oldName))
    }
}