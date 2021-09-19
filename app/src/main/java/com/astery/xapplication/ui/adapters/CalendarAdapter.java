package com.astery.xapplication.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astery.xapplication.R;
import com.astery.xapplication.ui.adapters.units.DayUnit;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    private final ArrayList<DayUnit> units;
    private BlockListener blockListener;
    private final Context context;
    private final Calendar now;

    private boolean isAvailable = true;

    public CalendarAdapter(ArrayList<DayUnit> blocks, Context context) {
        this.units = blocks;
        this.context = context;
        now = Calendar.getInstance();
    }

    public boolean isNotAvailable() {
        return !isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setBlockListener(BlockListener block_listener) {
        this.blockListener = block_listener;
    }
    public interface BlockListener {
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_calendar, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DayUnit unit = units.get(position);

        holder.day.setText(Integer.toString(unit.day));

        if (!unit.enabled){
            holder.card.setVisibility(GONE);
        }


    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView day;
        private final MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.day);
            card = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(v -> {
                if (blockListener != null) {
                    blockListener.onClick(getAdapterPosition());
                }
            });

        }
    }
}
