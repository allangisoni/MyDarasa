package com.mydarasa.app.alerts;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.recyclerview.widget.RecyclerView;

public class AlertsViewHolder extends RecyclerView.ViewHolder{
    public TextView tvAlertName;
    public TextView tvSchoolName;
    public   TextView tvStudentName;
    public TextView tvEventTime;
    public TextView tvStudentsIcon;
    public LinearLayout linearLayout;
    public  TextView tvDescription;
    RelativeLayout rlAlerts;
    View view;

    public AlertsViewHolder( View itemView) {
        super(itemView);

        tvAlertName = (TextView) itemView.findViewById(R.id.tv_eventName);
       // tvSchoolName = (TextView) itemView.findViewById(R.id.tv_schoolName);
        tvStudentName = (TextView) itemView.findViewById(R.id.tv_studentName);
        tvEventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
        tvStudentsIcon = (TextView) itemView.findViewById(R.id.tvStudentIcon);
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        rlAlerts = itemView.findViewById(R.id.rlAlerts);
        view = itemView.findViewById(R.id.view);
        //linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutRSVP);
    }
}
