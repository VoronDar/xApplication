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

    private ArrayList<DayUnit> units;
    private BlockListener blockListener;
    private int selectedDateId;

    public CalendarAdapter(ArrayList<DayUnit> blocks) {
        this.units = blocks;
        selectedDateId = 0;

    }

    public void setUnits(ArrayList<DayUnit> units) {
        this.units = units;
        selectedDateId = 0;
        notifyDataSetChanged();
    }

    public void setBlockListener(BlockListener block_listener) {
        this.blockListener = block_listener;
    }

    public void setSelectedDay(int day) {
        for (int i = 0; i < units.size(); i++){
            DayUnit unit = units.get(i);
            if (unit.day == day){
                selectedDateId = i;
                notifyDataSetChanged();
                break;
            }
        }
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

        if (position == selectedDateId){
            holder.day.setVisibility(GONE);
        } else
            holder.day.setVisibility(View.VISIBLE);


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
