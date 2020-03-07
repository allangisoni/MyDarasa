package com.mydarasa.app.progressreports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgressReportItemAdapter extends RecyclerView.Adapter<ProgressReportItemViewHolder> {
    private Context mContext;
    private List<ProgressReportItem> reportItemList;

    public  ProgressReportItemAdapter(Context context,List<ProgressReportItem> reportItemList ){

        this.mContext = context;
        this.reportItemList = reportItemList;
    }

    @NonNull
    @Override
    public ProgressReportItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.progress_report_item, parent, false);
        return new ProgressReportItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressReportItemViewHolder holder, int position) {
        ProgressReportItem reportItem =reportItemList.get(position);

        holder.tvfeeItemSubject.setText(reportItem.getSubject());
        holder.tvFeeItemScore.setText(reportItem.getScore());
        holder.tvGrade.setText(reportItem.getGrade());


    }

    @Override
    public int getItemCount() {
        return reportItemList.size();
    }
}
