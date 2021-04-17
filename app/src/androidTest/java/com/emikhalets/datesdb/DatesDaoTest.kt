package com.emikhalets.datesdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.database.DatesDao
import com.emikhalets.datesdb.model.entities.DateItem
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatesDaoTest {

    private lateinit var datesDao: DatesDao
    private lateinit var db: AppDatabase

    private val date1 = DateItem("name1", 1000, "type1", 0, 0, false, "")
    private val date2 = DateItem("name2", 1000, "type2", 0, 0, false, "")
    private val date3 = DateItem("name3", 1000, "type3", 0, 0, false, "")


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        datesDao = db.datesDao

        runBlocking {
            datesDao.insert(date1)
            datesDao.insert(date2)
            datesDao.insert(date3)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testGetDate() = runBlocking {
        assertThat(datesDao.getDate(1).name, equalTo(date1.name))
        assertThat(datesDao.getDate(2).name, equalTo(date2.name))
        assertThat(datesDao.getDate(3).name, equalTo(date3.name))
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllDates() = runBlocking {
        val list = datesDao.getAllDates()

        assertThat(list.size, equalTo(3))
        assertThat(list[0].name, equalTo(date1.name))
        assertThat(list[1].name, equalTo(date2.name))
        assertThat(list[2].name, equalTo(date3.name))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndDeleteDate() = runBlocking {
        val dateItem = DateItem("name4", 1000, "type4", 0, 0, false, "")
        datesDao.insert(dateItem)

        assertThat(datesDao.getAllDates().last().name, equalTo(dateItem.name))

        datesDao.delete(datesDao.getDate(4))

        assertThat(datesDao.getAllDates().size, equalTo(3))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndUpdateDate() = runBlocking {
        val dateItem = datesDao.getDate(1)
        val oldName = dateItem.name
        val newName = "name5"
        dateItem.name = newName
        datesDao.update(dateItem)

        assertThat(datesDao.getAllDates()[0].name, equalTo(newName))

        dateItem.name = oldName
        datesDao.update(dateItem)

        assertThat(datesDao.getAllDates()[0].name, equalTo(oldName))
    }
}