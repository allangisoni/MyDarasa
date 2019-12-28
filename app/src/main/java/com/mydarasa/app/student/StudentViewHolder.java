package com.mydarasa.app.student;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder  extends RecyclerView.ViewHolder {

    TextView tvStudentName;
    TextView tvSchoolName;
    TextView tvClassName;
    RelativeLayout rlStudents;

    public StudentViewHolder( View itemView) {
        super(itemView);

        this.tvStudentName = (TextView) itemView.findViewById(R.id.tv_Name);
        this.tvSchoolName =(TextView) itemView.findViewById(R.id.tv_schoolName);
        this.tvClassName = (TextView) itemView.findViewById(R.id.tv_className);
        rlStudents = (RelativeLayout) itemView.findViewById(R.id.rlStudents);
    }

}
