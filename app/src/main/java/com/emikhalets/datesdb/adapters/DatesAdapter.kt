package com.emikhalets.datesdb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.databinding.ItemDateBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.ViewHolder> {

    private List<DateItem> dates;
    private OnDateItemClickListener onDateItemClickListener;

    public interface OnDateItemClickListener {
        void onDateItemClick(int id);
    }

    public DatesAdapter(OnDateItemClickListener onDateItemClickListener) {
        dates = new ArrayList<>();
        this.onDateItemClickListener = onDateItemClickListener;
    }

    public void setDates(List<DateItem> dates) {
        sortingList(dates);
        this.dates = dates;
        notifyDataSetChanged();
    }

    public void sortingList(List<DateItem> dates) {
        boolean isSorted = false;
        int counter = 0;

        while (!isSorted) {
            for (int y = 0; y < dates.size() - 1; y++) {
                Calendar current = Calendar.getInstance();
                int currentDay = current.get(Calendar.DAY_OF_YEAR);

                Calendar first = Calendar.getInstance();
                first.setTimeInMillis(dates.get(y).getDate());
                int firstDay = first.get(Calendar.DAY_OF_YEAR);

                int firstDaysLeft = 0;

                if (currentDay > firstDay) {
                    firstDaysLeft = 365 + firstDay - currentDay;
                } else {
                    firstDaysLeft = firstDay - currentDay;
                }

                Calendar second = Calendar.getInstance();
                second.setTimeInMillis(dates.get(y + 1).getDate());
                int secondDay = second.get(Calendar.DAY_OF_YEAR);

                int secondDaysLeft = 0;

                if (currentDay > secondDay) {
                    secondDaysLeft = 365 + secondDay - currentDay;
                } else {
                    secondDaysLeft = secondDay - currentDay;
                }

                if (firstDaysLeft > secondDaysLeft) {
                    counter++;
                    DateItem temp = dates.get(y);
                    dates.add(y, dates.get(y + 1));
                    dates.remove(y + 1);
                    dates.add(y + 1, temp);
                    dates.remove(y + 2);
                }
            }

            if (counter > 0) {
                counter = 0;
            } else {
                isSorted = true;
            }
        }
    }

    @NonNull
    @Override
    public DatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDateBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DatesAdapter.ViewHolder holder, int position) {
        holder.bind(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemDateBinding binding;

        public ViewHolder(ItemDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DateItem dateItem) {
            Calendar current = Calendar.getInstance();
            int currentDay = current.get(Calendar.DAY_OF_YEAR);

            Calendar selected = Calendar.getInstance();
            selected.setTimeInMillis(dateItem.getDate());
            int selectedDay = selected.get(Calendar.DAY_OF_YEAR);

            int daysLeft = 0;

            if (currentDay > selectedDay) {
                daysLeft = 365 + selectedDay - currentDay;
            } else {
                daysLeft = selectedDay - currentDay;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL y ', ' EEEE");

            binding.textName.setText(dateItem.getName());
            binding.textDate.setText(dateFormat.format(dateItem.getDate()));
            binding.textDaysLeft.setText(String.valueOf(dateItem.getId()));
            binding.textDaysLeft.setText(String.valueOf(daysLeft));

            binding.getRoot().setOnClickListener(
                    v -> onDateItemClickListener.onDateItemClick(dateItem.getId()));
        }
    }
}
