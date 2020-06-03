package com.emikhalets.datesdb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.databinding.ItemDateBinding;

import java.util.ArrayList;
import java.util.List;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.ViewHolder> {

    private List<DateItem> dates;
    private OnDateClickListener onDateClickListener;

    public interface OnDateClickListener {
        void onClick(DateItem dateItem);
    }

    public DatesAdapter(OnDateClickListener onDateClickListener) {
        dates = new ArrayList<>();
        this.onDateClickListener = onDateClickListener;
    }

    public void setDates(List<DateItem> dates) {
        this.dates = dates;
        notifyDataSetChanged();
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
        return dates.isEmpty() ? 0 : dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemDateBinding binding;
        private OnDateClickListener listener;

        public ViewHolder(ItemDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            listener = onDateClickListener;
        }

        void bind(DateItem dateItem) {
            binding.textName.setText(dateItem.getName());
            binding.textDate.setText(dateItem.getDate());
            binding.textDaysLeft.setText(String.valueOf(dateItem.getId()));
            binding.getRoot().setOnClickListener(v -> listener.onClick(dateItem));
        }
    }
}
