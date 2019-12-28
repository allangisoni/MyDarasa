package com.mydarasa.app.events;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsViewHolder extends RecyclerView.ViewHolder {

   public TextView tvEventName;
    public TextView tvSchoolName;
    public   TextView tvStudentName;
    public  TextView tvEventDate;
    public TextView tvEventTime;
    public TextView tvRSVP;
    public ImageView ivTickCancel;
    public LinearLayout linearLayout;

    public EventsViewHolder( View itemView) {
        super(itemView);

        tvEventName = (TextView) itemView.findViewById(R.id.tv_eventName);
        tvSchoolName = (TextView) itemView.findViewById(R.id.tv_schoolName);
        tvStudentName = (TextView) itemView.findViewById(R.id.tv_studentName);
        tvEventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
        tvEventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
        ivTickCancel = (ImageView) itemView.findViewById(R.id.ivtick_cancel);
        tvRSVP = (TextView) itemView.findViewById(R.id.tvRSVP);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutRSVP);
    }
}
