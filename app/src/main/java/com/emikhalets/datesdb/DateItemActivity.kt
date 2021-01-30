package com.emikhalets.datesdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emikhalets.datesdb.databinding.ActivityDateItemBinding;
import com.emikhalets.datesdb.utils.Const;
import com.emikhalets.datesdb.viewmodels.DateItemViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateItemActivity extends AppCompatActivity {

    private DateItemViewModel viewModel;
    private ActivityDateItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(DateItemViewModel.class);

        viewModel.getLiveDataNotice().observe(this, notice -> {
            if (notice.equals("DATE_ITEM")) {
                fillFields();
                binding.layoutDateItem.animate().alpha(1).setDuration(1000).start();
            } else if (notice.equals("DELETED")) {
                finish();
            }
        });

        getDateFromIntent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_date_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                viewModel.delete(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDateFromIntent() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(Const.EXTRA_DATE_ID, -1);

        if (id != -1) {
            viewModel.getDate(this, id);
        }
    }

    private void fillFields() {
        Calendar current = Calendar.getInstance();
        int currentDay = current.get(Calendar.DAY_OF_YEAR);

        Calendar selected = Calendar.getInstance();
        selected.setTimeInMillis(viewModel.getDateItem().getDate());
        int selectedDay = selected.get(Calendar.DAY_OF_YEAR);
        int selectedHour = current.get(Calendar.HOUR);
        int selectedMin = current.get(Calendar.MINUTE);
        int selectedSec = current.get(Calendar.SECOND);

        int daysLeft = 0;

        if (currentDay > selectedDay) {
            daysLeft = 365 + selectedDay - currentDay;
        } else {
            daysLeft = selectedDay - currentDay;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL y ', ' EEEE");

        binding.textName.setText(viewModel.getDateItem().getName());
        binding.textDate.setText(dateFormat.format(viewModel.getDateItem().getDate()));
        binding.textType.setText(viewModel.getDateItem().getType());

        binding.textDaysLeft.setText(String.valueOf(daysLeft - 1));
        binding.textHoursLeft.setText(String.valueOf(24 - selectedHour));
        binding.textMinutesLeft.setText(String.valueOf(60 - selectedMin));
        binding.textSecondsLeft.setText(String.valueOf(60 - selectedSec));
    }
}
