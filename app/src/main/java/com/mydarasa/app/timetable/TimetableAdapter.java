package com.mydarasa.app.timetable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;
import com.mydarasa.app.cocurricular.CocurricularTrainingActivity;
import com.mydarasa.app.cocurricular.TrainingDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimetableAdapter extends RecyclerView.Adapter<timetableViewHolder> {

    private List<TimetableItemModel> timetableItemModelList;
    private Context mContext;

    public TimetableAdapter(Context context, List<TimetableItemModel> timetableItemModelList){

        this.mContext = context;
        this.timetableItemModelList = timetableItemModelList;
    }
    @NonNull
    @Override
    public timetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timetable_list, parent,false);
        return new timetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull timetableViewHolder holder, int position) {
        final TimetableItemModel timetableItemModel = timetableItemModelList.get(position);

        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));

        String startTime = timetableItemModel.getStartTime();
        String endTime = timetableItemModel.getEndTime();
        SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat oFormatter = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat tFormatter = new SimpleDateFormat("HH:mm");
        Date startDate = null;
        Date endDate =null;
        try {
            startDate = iFormatter.parse(startTime);
            endDate = iFormatter.parse(endTime);

        }catch (Exception e){

        }

        String formattedStartTime = tFormatter.format(startDate);
        String formattedEndTime = tFormatter.format(endDate);
        holder.tvstartTime.setText(formattedStartTime);
        holder.tvendTime.setText(formattedEndTime);
        holder.tvSujectName.setText(timetableItemModel.getItemName() + " "+ " class");
        holder.tvTutor.setText(timetableItemModel.getTeacherName());
        holder.view.setBackgroundColor(color);
        holder.rlTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timetableItemModel.getItemName() != ""){
                    if (timetableItemModel.getItemName().equals("Classic Yoga")){
                        mContext.startActivity(new Intent(mContext, TrainingDetailsActivity.class));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timetableItemModelList.size();
    }

    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
