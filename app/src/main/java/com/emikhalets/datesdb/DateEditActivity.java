package com.emikhalets.datesdb;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emikhalets.datesdb.databinding.ActivityDateEditBinding;
import com.emikhalets.datesdb.viewmodels.DateEditViewModel;

public class DateEditActivity extends AppCompatActivity {

    private ActivityDateEditBinding binding;
    private DateEditViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(DateEditViewModel.class);
    }

    @Override
    public void onBackPressed() {
        String name = binding.etName.getText().toString().trim();
        String date = binding.etDate.getText().toString().trim();
        String type = binding.etType.getText().toString().trim();

        if (!name.isEmpty() && !date.isEmpty() && !type.isEmpty()) {
            viewModel.insert(name, date, type);
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Все поля должны быть заполнены",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
