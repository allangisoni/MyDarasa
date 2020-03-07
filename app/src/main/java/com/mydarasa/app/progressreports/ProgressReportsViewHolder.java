package com.mydarasa.app.progressreports;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProgressReportsViewHolder extends RecyclerView.ViewHolder {

    TextView tvReportName, tvReportDescription, tvReportRemarks, tvStudentName, tvReportTerm, tvReportYear;
    CardView cvProgressReport;
    public ProgressReportsViewHolder(@NonNull View itemView) {
        super(itemView);

        tvReportName = itemView.findViewById(R.id.tvreportName);
        tvReportDescription = itemView.findViewById(R.id.tvreportDescription);
        tvReportRemarks = itemView.findViewById(R.id.tvRemarks);
        tvStudentName = itemView.findViewById(R.id.tvprogressStudentName);
        cvProgressReport = itemView.findViewById(R.id.cvProgressReport);
        tvReportTerm =itemView.findViewById(R.id.tvreportTerm);
        tvReportYear = itemView.findViewById(R.id.tvreportYear);

    }


}
