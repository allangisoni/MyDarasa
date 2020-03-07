package com.mydarasa.app.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentPickerAdapter  extends RecyclerView.Adapter<StudentPickerViewHolder> {
    private List<StudentModel> studentPickerModelList;

    private final Context mContext;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(StudentModel studentPickerModel, int pos);
    }


    public  StudentPickerAdapter(List<StudentModel> studentPickerModelList, Context context, OnItemClickListener listener){

        this.studentPickerModelList = studentPickerModelList;
        this.mContext = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public StudentPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.student_list_picker, parent, false);
        return new StudentPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentPickerViewHolder holder, int position) {

        holder.bind(studentPickerModelList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return studentPickerModelList.size();
    }
    public void refreshList(){
        notifyDataSetChanged();
    }
}
