package com.emikhalets.datesdb.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface DatesDao {

	@Insert
	void insert(DateItem date);

	@Update
	void update(DateItem date);

	@Delete
	void delete(DateItem date);

	@Query("SELECT * FROM dates_table WHERE id = :id")
	DateItem getDate(int id);

	@Query("SELECT * FROM dates_table")
	Single<List<DateItem>> getAllDates();
}
