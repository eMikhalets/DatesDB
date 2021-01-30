package com.emikhalets.datesdb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emikhalets.datesdb.databinding.ActivityDateAddBinding;
import com.emikhalets.datesdb.viewmodels.DateAddViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAddActivity extends AppCompatActivity {

    private DateAddViewModel viewModel;
    private ActivityDateAddBinding binding;

    private long selectedDate;
    private boolean isInserted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(DateAddViewModel.class);

        viewModel.getLiveDataNotice().observe(this, notice -> {
            if (notice.equals("OK")) {
                isInserted = true;
                finish();
            }
        });

        binding.btnDatePick.setOnClickListener(view -> onBtnDatePickerClick());

        initPickDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_date_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_save:
                onClickSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isInserted) {
            super.onBackPressed();
        }
    }

    private void onClickSave() {
        String name = binding.etName.getText().toString().trim();
        String type = binding.spinnerDateType.getSelectedItem().toString();
        viewModel.insert(this, name, selectedDate, type);
    }

    private void onBtnDatePickerClick() {
        Calendar current = Calendar.getInstance();

        new DatePickerDialog(this, dateSetListener,
                current.get(Calendar.YEAR),
                current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
        Calendar selected = Calendar.getInstance();
        selected.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL y ', ' EEEE");
        binding.btnDatePick.setText(dateFormat.format(selected.getTime()));
        selectedDate = selected.getTimeInMillis();
    };

    private void initPickDate() {
        Calendar current = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL y ', ' EEEE");
        binding.btnDatePick.setText(dateFormat.format(current.getTime()));
        selectedDate = current.getTimeInMillis();
    }
}