package com.mydarasa.app.progressreports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgressReportsAdapter extends RecyclerView.Adapter<ProgressReportsViewHolder> {

    private Context mContext;
    private List<ProgressModel> progressModelList;

    public ProgressReportsAdapter(Context context, List<ProgressModel> progressModelList){

        this.mContext = context;
        this.progressModelList = progressModelList;
    }

    @NonNull
    @Override
    public ProgressReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_progressreports_list, parent, false);
        return new ProgressReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressReportsViewHolder holder, int position) {
      ProgressModel progressModel = progressModelList.get(position);

      holder.tvReportName.setText(progressModel.getName());
      holder.tvReportDescription.setText(progressModel.getReportDescription());
      holder.tvReportRemarks.setText(progressModel.getRemarks());
      holder.tvStudentName.setText(progressModel.getStudentName());

      if(!progressModel.getRemarks().isEmpty()){
          if(progressModel.getRemarks().equals("Good")){
              holder.tvReportRemarks.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
          } else  if(progressModel.getRemarks().equals("Average")){
              holder.tvReportRemarks.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
          }
      }

    }

    @Override
    public int getItemCount() {
        return progressModelList.size();
    }
}
