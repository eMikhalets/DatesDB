package com.emikhalets.datesdb;

import android.os.Bundle;

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
}
