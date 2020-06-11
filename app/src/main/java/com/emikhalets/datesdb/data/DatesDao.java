package com.emikhalets.datesdb.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatesDao {

	@Insert
	public void insert(DateItem date);

	@Update
	public void update(DateItem date);

	@Delete
	public void delete(DateItem date);

	@Query("SELECT * FROM dates_table WHERE id = :id")
	public DateItem getDate(int id);

	@Query("SELECT * FROM dates_table")
	public List<DateItem> getAllDates();
}
