package com.mydarasa.app.progressreports;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgressReportItemViewHolder extends RecyclerView.ViewHolder {

    TextView tvfeeItemSubject, tvFeeItemScore, tvGrade;
    public ProgressReportItemViewHolder(@NonNull View itemView) {
        super(itemView);

        tvfeeItemSubject = itemView.findViewById(R.id.feeItemSubject);
        tvFeeItemScore = itemView.findViewById(R.id.feeItemScore);
        tvGrade = itemView.findViewById(R.id.tvfeeItemGrade);
    }
}
