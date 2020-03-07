package com.mydarasa.app.student;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentPickerViewHolder extends RecyclerView.ViewHolder {

    TextView tvStudentIcon, tvStudentName;
    Boolean isSelected = false;
    private int checkedPosition = -1;

    public StudentPickerViewHolder(@NonNull View itemView) {
        super(itemView);

        tvStudentIcon = itemView.findViewById(R.id.tvStudentIcon);
       // tvStudentName = itemView.findViewById(R.id.tvStudName);
    }


    public void bind(final StudentModel studentPickerModel, final StudentPickerAdapter.OnItemClickListener listener) {

     String mName = studentPickerModel.getName();
     mName = mName.substring(0, 1);
     tvStudentIcon.setText(mName);

     //tvStudentName.setText(studentPickerModel.getName());
    // tvStudentName.setVisibility(View.INVISIBLE);

     Random mRandom = new Random();
     int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
     ((GradientDrawable) tvStudentIcon.getBackground()).setColor(color);

     itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             checkedPosition = getAdapterPosition();
             listener.onItemClick(studentPickerModel, getAdapterPosition());



            /** if(isSelected == false) {
                 isSelected = true;
                 Random mRandom = new Random();
                 int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                 ((GradientDrawable) tvStudentIcon.getBackground()).setColor(color);
             } else {
                 isSelected = false;
                 ((GradientDrawable) tvStudentIcon.getBackground()).setColor(Color.GRAY);
             } **/

         }
     });
    }
}
