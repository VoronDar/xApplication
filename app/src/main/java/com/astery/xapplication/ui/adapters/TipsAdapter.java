package com.astery.xapplication.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.astery.xapplication.R;
import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.ui.adapters.units.AdvicesUnit;

import java.util.ArrayList;

import static android.view.View.GONE;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder>{

    private ArrayList<AdvicesUnit> units;
    private BlockListener blockListener;
    private final Fragment context;

    public TipsAdapter(ArrayList<AdvicesUnit> units, Fragment context) {
        this.units = units;
        this.context = context;
    }

    public void setUnits(ArrayList<AdvicesUnit> units) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_tip, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AdvicesUnit unit = units.get(position);

        holder.questionText.setText(unit.getQuestion());
        holder.answer.setText(unit.getAnswer());
        holder.questionNumber.setText(position+1+"");

        for (Advise advice: unit.getAdvices()){
            //LinearLayout adviceLayout = new LinearLayout(context.getContext());
            //adviceLayout.setOrientation(LinearLayout.VERTICAL);
            //adviceLayout.addView(context.getLayoutInflater().inflate(R.layout.unit_advice, null));
            //((TextView)adviceLayout.findViewById(R.id.advice_text)).setText(advice.getText());
            holder.tipLayout.addView(context.getLayoutInflater().inflate(R.layout.unit_advice, null));
        }


    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView questionNumber;
        final TextView questionText;
        final TextView answer;
        final LinearLayout tipLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionNumber = itemView.findViewById(R.id.question_number);
            questionText = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            tipLayout = itemView.findViewById(R.id.tip_layout);

            itemView.setOnClickListener(v -> {
                if (blockListener != null) {
                    blockListener.onClick(getAdapterPosition());
                }
            });

        }
    }
}
