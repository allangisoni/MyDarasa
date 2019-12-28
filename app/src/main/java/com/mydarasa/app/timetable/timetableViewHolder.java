package com.mydarasa.app.timetable;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class timetableViewHolder extends RecyclerView.ViewHolder {

    TextView tvstartTime, tvendTime, tvSujectName, tvTutor;
    public timetableViewHolder(@NonNull View itemView) {
        super(itemView);

        tvstartTime = itemView.findViewById(R.id.tvStartTime);
        tvendTime = itemView.findViewById(R.id.tvendTime);
        tvSujectName = itemView.findViewById(R.id.tvsubjectName);
        tvTutor = itemView.findViewById(R.id.tvTutor);
    }
}
