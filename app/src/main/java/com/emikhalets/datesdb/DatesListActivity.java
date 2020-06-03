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
import com.emikhalets.datesdb.databinding.ActivityDatesListBinding;
import com.emikhalets.datesdb.viewmodels.DatesListViewModel;

public class DatesListActivity extends AppCompatActivity {

    private static final int ADD_DATE_REQUEST = 0;
    private static final int DATE_ITEM_REQUEST = 1;
    private static final String DATE_ITEM_EXTRA = "date_item_extra";
    private static final String NAME_EXTRA = "name_extra";
    private static final String DATE_EXTRA = "date_extra";
    private static final String TYPE_EXTRA = "type_extra";

    private ActivityDatesListBinding binding;
    private DatesListViewModel viewModel;
    private DatesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(DatesListViewModel.class);

        adapter = new DatesAdapter(dateItem -> {
            Intent intent = new Intent(this, DateItemActivity.class);
            intent.putExtra(DATE_ITEM_EXTRA, dateItem);
            startActivity(intent);
        });

        viewModel.getLiveDataDatesList().observe(this, dates -> {
            if (dates != null) {
                adapter.setDates(dates);

                if (!dates.isEmpty()) {
                    hideAddDateHint();
                } else {
                    showAddDateHint();
                }
            }
        });

        binding.fabAddDate.setOnClickListener(v -> {
            Intent intent = new Intent(this, DateEditActivity.class);
            startActivity(intent);
        });

        binding.recyclerDates.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerDates.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getAllDates();
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

    private void showAddDateHint() {
        binding.textAddFirstDate.setVisibility(View.VISIBLE);
    }

    private void hideAddDateHint() {
        binding.textAddFirstDate.setVisibility(View.INVISIBLE);
    }
}
