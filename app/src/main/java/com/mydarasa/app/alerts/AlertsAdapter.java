package com.mydarasa.app.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydarasa.app.R;
import com.mydarasa.app.chat.ChatActivity;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.events.EventsViewHolder;
import com.mydarasa.app.progressreports.ProgressReportDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsViewHolder> {

    List<AlertsModel> alertsModelList;
    Context mContext;
    Boolean isRSVP = false;
    TextView tvAlertStudent, tvAlertTvTitle, tvAlertTvDescription, tvAlertImportance, tvAlertTime, tvComment;
    Button btnAlertOk;

    public AlertsAdapter(Context context,List<AlertsModel> alertsModelLists ){
        this.mContext = context;
        this.alertsModelList =alertsModelLists;
    }


    @Override
    public AlertsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts_item_list, parent, false);

        return new AlertsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlertsViewHolder holder, int position) {

        final AlertsModel alertsModel = alertsModelList.get(position);

        holder.tvAlertName.setText(alertsModel.getAlertTitle());
        //holder.tvSchoolName.setText(eventsModel.getSchool());
        //holder.tvStudentName.setText(eventsModel.getStudentName());

        String eventDate = alertsModel.getAlertDate();
        SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat oFormatter = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat tFormatter = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = iFormatter.parse(eventDate);
        }catch (Exception e){

        }

        String formattedDate = oFormatter.format(date);
        final String formattedTime = tFormatter.format(date);

        //holder.tvEventDate.setText(formattedDate);
        holder.tvEventTime.setText(formattedTime);
        holder.tvDescription.setText(alertsModel.getDescription());
        holder.tvStudentName.setText(alertsModel.getStudentName());
        holder.tvStudentsIcon.setText(alertsModel.getAlertType().substring(0,1));
        if(alertsModel.getAlertCode().toLowerCase().equals("high")) {
            int res = mContext.getResources().getColor(R.color.color_red_rsvp);
            holder.view.setBackgroundColor(res);
        } else  if(alertsModel.getAlertCode().toLowerCase().equals("low")) {
            int res = mContext.getResources().getColor(R.color.colorPrimary);
            holder.view.setBackgroundColor(res);
        } else{
            int res = mContext.getResources().getColor(android.R.color.holo_orange_light);
            holder.view.setBackgroundColor(res);
        }
        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.tvStudentsIcon.getBackground()).setColor(color);

        holder.rlAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("");

                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View view = layoutInflater.inflate(R.layout.alert_details_dialog, null);

                tvAlertStudent = view.findViewById(R.id.alertTvStudent);
                tvAlertTvTitle = view.findViewById(R.id.alertTvTitle);
                tvAlertTvDescription = view.findViewById(R.id.alertTvDescription);
                tvAlertImportance = view.findViewById(R.id.alertTVImportance);
                tvAlertTime = view.findViewById(R.id.alertTVTime);
                btnAlertOk = view.findViewById(R.id.btnAlertOk);
                tvComment = view.findViewById(R.id.tvComment);
                tvAlertStudent.setText(alertsModel.getStudentName());
                tvAlertTvTitle.setText(alertsModel.getAlertTitle());
                tvAlertTvDescription.setText(alertsModel.getDescription());
                tvAlertImportance.setText("Importance:" +" "+ alertsModel.getAlertCode());
                tvAlertTime.setText(formattedTime);



                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnAlertOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      alertDialog.dismiss();
                    }
                });

                tvComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        String gsonString =  gson.toJson(alertsModel);

                        Intent intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("gsonString", gsonString);
                        mContext.startActivity(intent);
                    }
                });

               /** builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }); **/


            }
        });


       /** holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRSVP) {
                    holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_tick));
                    holder.tvRSVP.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    isRSVP = true;
                } else {
                    holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cancel));
                    holder.tvRSVP.setTextColor(mContext.getResources().getColor(R.color.color_red_rsvp));
                    isRSVP = false;
                }
            }
        }); **/

    }

    @Override
    public int getItemCount() {
        return alertsModelList.size();
    }

    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
