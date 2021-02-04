package com.emikhalets.datesdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.database.TypesDao
import com.emikhalets.datesdb.model.entities.DateType
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TypesDaoTest {

    private lateinit var typesDao: TypesDao
    private lateinit var db: AppDatabase

    private val type1 = DateType("type1")
    private val type2 = DateType("type2")
    private val type3 = DateType("type3")


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        typesDao = db.typesDao

        runBlocking {
            typesDao.insert(type1)
            typesDao.insert(type2)
            typesDao.insert(type3)
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
        assertThat(typesDao.getType("type1").name, equalTo(type1.name))
        assertThat(typesDao.getType("type2").name, equalTo(type2.name))
        assertThat(typesDao.getType("type3").name, equalTo(type3.name))
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllTypes() = runBlocking {
        val list = typesDao.getAllTypes()

        assertThat(list.size, equalTo(3))
        assertThat(list[0].name, equalTo(type1.name))
        assertThat(list[1].name, equalTo(type2.name))
        assertThat(list[2].name, equalTo(type3.name))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndDeleteType() = runBlocking {
        val type = DateType("type4")
        typesDao.insert(type)

        assertThat(typesDao.getAllTypes().last().name, equalTo(type.name))

        typesDao.delete(typesDao.getAllTypes()[3])

        assertThat(typesDao.getAllTypes().size, equalTo(3))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndUpdateType() = runBlocking {
        val type = typesDao.getAllTypes()[0]
        val oldName = type.name
        val newName = "name5"
        type.name = newName
        typesDao.update(type)

        assertThat(typesDao.getAllTypes()[0].name, equalTo(newName))

        type.name = oldName
        typesDao.update(type)

        assertThat(typesDao.getAllTypes()[0].name, equalTo(oldName))
    }
}