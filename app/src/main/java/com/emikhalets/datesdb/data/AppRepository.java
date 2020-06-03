package com.emikhalets.datesdb.data;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppRepository {

    private DatesDao datesDao;

    public AppRepository(DatesDao datesDao) {
        this.datesDao = datesDao;
    }

    public void insert(DateItem date) {
        new InsertTask().execute(date);
    }

    public void update(DateItem date) {
        new UpdateTask().execute(date);
    }

    public void delete(DateItem date) {
        new DeleteTask().execute(date);
    }

    public DateItem getDate(int id) {
        GetDateTask task = new GetDateTask();
        try {
            return task.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DateItem> getAllDates() {
        GetAllDatesTask task = new GetAllDatesTask();
        try {
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class InsertTask extends AsyncTask<DateItem, Void, Void> {

        @Override
        protected Void doInBackground(DateItem... dateItems) {
            datesDao.insert(dateItems[0]);
            return null;
        }
    }

    private class UpdateTask extends AsyncTask<DateItem, Void, Void> {

        @Override
        protected Void doInBackground(DateItem... dateItems) {
            datesDao.update(dateItems[0]);
            return null;
        }
    }

    private class DeleteTask extends AsyncTask<DateItem, Void, Void> {

        @Override
        protected Void doInBackground(DateItem... dateItems) {
            datesDao.delete(dateItems[0]);
            return null;
        }
    }

    private class GetDateTask extends AsyncTask<Integer, Void, DateItem> {

        @Override
        protected DateItem doInBackground(Integer... integers) {
            return datesDao.getDate(integers[0]);
        }
    }

    private class GetAllDatesTask extends AsyncTask<Void, Void, List<DateItem>> {

        @Override
        protected List<DateItem> doInBackground(Void... voids) {
            return datesDao.getAllDates();
        }
    }
}
