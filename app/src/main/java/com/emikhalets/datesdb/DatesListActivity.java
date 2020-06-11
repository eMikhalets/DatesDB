package com.emikhalets.datesdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emikhalets.datesdb.adapters.DatesAdapter;
import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.databinding.ActivityDatesListBinding;
import com.emikhalets.datesdb.utils.Const;
import com.emikhalets.datesdb.viewmodels.DatesListViewModel;

import java.util.List;

public class DatesListActivity extends AppCompatActivity
        implements DatesAdapter.OnDateItemClickListener {

    private DatesAdapter adapter;
    private DatesListViewModel viewModel;
    private ActivityDatesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(DatesListViewModel.class);

        viewModel.getLiveDataDates().observe(this, this::onDatesListChanged);

        adapter = new DatesAdapter(this);
        binding.recyclerDates.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerDates.setAdapter(adapter);

        binding.fabAddDate.setOnClickListener(v -> onAddDateClick());
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getAllDates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dates_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_search:
                // search in list
                return true;
            case R.id.menu_list_settings:
                // go to settings activity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateItemClick(int id) {
        Intent intent = new Intent(this, DateItemActivity.class);
        intent.putExtra(Const.EXTRA_DATE_ID, id);
        startActivity(intent);
    }

    private void onAddDateClick() {
        Intent intent = new Intent(this, DateAddActivity.class);
        startActivity(intent);
    }

    private void onDatesListChanged(List<DateItem> dates) {
        if (dates != null) {
            adapter.setDates(dates);
            handleTextEmptyListVisibility(dates);
        }
    }

    private void handleTextEmptyListVisibility(List<DateItem> dates) {
        if (!dates.isEmpty()) {
            binding.textAddFirstDate.setVisibility(View.INVISIBLE);
        } else {
            binding.textAddFirstDate.setVisibility(View.VISIBLE);
        }
    }
}
