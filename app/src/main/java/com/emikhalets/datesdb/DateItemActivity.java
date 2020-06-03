package com.emikhalets.datesdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.databinding.ActivityDateItemBinding;
import com.emikhalets.datesdb.viewmodels.ItemDateViewModel;

public class DateItemActivity extends AppCompatActivity {

    private static final String DATE_ITEM_EXTRA = "date_item_extra";

    private ItemDateViewModel viewModel;
    private ActivityDateItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(ItemDateViewModel.class);

        intentExtra();

        viewModel.getLiveDataFields().observe(this, fields -> {
            binding.textName.setText(fields.get("name"));
            binding.textDate.setText(fields.get("date"));
            binding.textType.setText(fields.get("type"));
        });
    }

    @Override
    public void onBackPressed() {
//        String name = binding.etName.getText().toString().trim();
//        String date = binding.etDate.getText().toString().trim();
//        String type = binding.etType.getText().toString().trim();
//
//        if (!name.isEmpty() && !date.isEmpty() && !type.isEmpty()) {
//            viewModel.insert(name, date, type);
//            super.onBackPressed();
//        } else {
//            Toast.makeText(this, "Все поля должны быть заполнены",
//                    Toast.LENGTH_SHORT).show();
//        }
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
                viewModel.delete(viewModel.getDate());
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void intentExtra() {
        Intent intent = getIntent();

        DateItem dateItem = (DateItem) intent.getSerializableExtra(DATE_ITEM_EXTRA);

        if (dateItem != null) {
            viewModel.setDate(dateItem);
            viewModel.setFields();
        }
    }
}
