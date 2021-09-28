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
import com.astery.xapplication.ui.views.utils.VS;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    private ArrayList<DayUnit> units;
    private BlockListener blockListener;
    private int selectedDay;
    private final Context context;

    public CalendarAdapter(ArrayList<DayUnit> blocks, Context context) {
        this.units = blocks;
        selectedDay = 0;
        this.context = context;

    }

    public void setUnits(ArrayList<DayUnit> units) {
        this.units = units;
        selectedDay = 0;
        notifyDataSetChanged();
    }

    public ArrayList<DayUnit> getUnits() {
        return units;
    }

    public void setBlockListener(BlockListener block_listener) {
        this.blockListener = block_listener;
    }

    public void setSelectedDay(int day) {
        this.selectedDay = day;
        notifyDataSetChanged();
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

        holder.card.setVisibility(VS.Companion.get(unit.enabled));

        if (unit.day==selectedDay){
         holder.card.setBackgroundColor(context.getResources().getColor(R.color.selected_card_color));
         holder.card.setStrokeColor(context.getResources().getColor(R.color.black));
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
