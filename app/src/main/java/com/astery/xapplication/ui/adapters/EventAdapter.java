package com.astery.xapplication.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astery.xapplication.R;
import com.astery.xapplication.pojo.Event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    private ArrayList<Event> units;
    private BlockListener blockListener;
    private final Context context;

    public EventAdapter(ArrayList<Event> blocks, Context context) {
        this.units = blocks;
        this.context = context;

    }

    public void setUnits(ArrayList<Event> units) {
        this.units = units;
        notifyDataSetChanged();
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_close_event, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Event unit = units.get(position);

        if (unit == null){
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.googleg_standard_color_18));
        }
        else
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.dating));

    }

    @Override
    public int getItemCount() {
        if (units == null)
            return 0;
        return units.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (blockListener != null) {
                    blockListener.onClick(getAdapterPosition());
                }
            });

        }
    }
}
