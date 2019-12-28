package com.mydarasa.app.student;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydarasa.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter< StudentViewHolder> {
    private Context mContext;
    List<StudentModel> studentList ;

    public StudentAdapter(Context context, List<StudentModel> studentLists) {

        this.mContext = context;
        this.studentList = studentLists;
    }



    @Override
    public StudentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.children_item_list, parent, false);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
      // StudentListModel studentListModel = studentList.get(position);
       final StudentModel student = studentList.get(position);

        //holder.tvStudentName.setText("djdkkkd");
       holder.tvStudentName.setText( student.getName() );
       holder.tvSchoolName.setText( student.getSchoolId() + " "+"School");
       holder.tvClassName.setText("Class"+" "+student.getClassId());

       holder.rlStudents.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String id = Integer.toString(student.getId());
               Intent intent = new Intent(mContext, StudentInfoActivity.class);
               intent.putExtra("studentId", id);

               mContext.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
