package com.mydarasa.app.cocurricular;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CocurricularViewHolder extends RecyclerView.ViewHolder {

    TextView tvCocurricularName, tvCocurricularType, tvCocurricularStudentName;

    public CocurricularViewHolder(@NonNull View itemView) {
        super(itemView);

        tvCocurricularName = itemView.findViewById(R.id.tvCocuricularName);
        tvCocurricularType = itemView.findViewById(R.id.tvCocuricularType);
        tvCocurricularStudentName = itemView.findViewById(R.id.tvCocuricularStudentName);
    }
}
