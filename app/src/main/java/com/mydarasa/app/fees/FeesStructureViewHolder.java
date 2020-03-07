package com.mydarasa.app.fees;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FeesStructureViewHolder extends RecyclerView.ViewHolder {

    TextView tvfeeStructureType, tvfeeAmount, tvFeeStructureDate, tvStudent, tvReportTerm, tvReportYear;
    CardView cvfeeStructure;

    public FeesStructureViewHolder(@NonNull View itemView) {
        super(itemView);

        tvfeeStructureType = itemView.findViewById(R.id.tvfeeStructure);
        tvfeeAmount = itemView.findViewById(R.id.tvfeeAmount);
        //tvFeeStructureDate = itemView.findViewById(R.id.tvfeeStruDate);
        tvStudent = itemView.findViewById(R.id.tvStudent);
        cvfeeStructure = itemView.findViewById(R.id.cvFeeDetails);
        tvReportTerm =itemView.findViewById(R.id.tvreportTerm);
        tvReportYear = itemView.findViewById(R.id.tvreportYear);
    }
}
