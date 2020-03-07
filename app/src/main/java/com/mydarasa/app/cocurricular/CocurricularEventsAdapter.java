package com.mydarasa.app.cocurricular;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.events.EventsViewHolder;
import com.mydarasa.app.events.RsvpModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CocurricularEventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {

    List<EventsModel> eventsModelList;
    Context mContext;
    Boolean isRSVP = false;
    PrefManager prefManager;
    String accessToken = " ";
    int dialogOption = 2;
    int att =0;
    int checkedItem = 0;

    public CocurricularEventsAdapter(Context context,List<EventsModel> eventsModelLists ){
        this.mContext = context;
        this.eventsModelList =eventsModelLists;
        this.prefManager = new PrefManager(mContext);
        accessToken = prefManager.getAccessToken();
    }


    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_list, parent, false);

        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventsViewHolder holder, int position) {

        final EventsModel eventsModel = eventsModelList.get(position);

        holder.tvEventName.setText(eventsModel.getEventName());
        holder.tvSchoolName.setText(eventsModel.getSchool());
        holder.tvStudentName.setText(eventsModel.getStudentName());

        String eventDate = eventsModel.getEventDate();
        SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat oFormatter = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat tFormatter = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = iFormatter.parse(eventDate);
        }catch (Exception e){

        }

        String formattedDate = oFormatter.format(date);
        String formattedTime = tFormatter.format(date);

        holder.tvEventDate.setText(formattedDate);
        holder.tvEventTime.setText(formattedTime);
        holder.ivTickCancel.setVisibility(View.GONE);
        holder.tvAttendance.setVisibility(View.GONE);
        holder.tvRSVP.setVisibility(View.GONE);
        holder.view.setVisibility(View.GONE);
       // holder.tvAttendance.setText("Attending:"+ " "+ eventsModel.getRsvp());

        String tvRsvpAttendance = eventsModel.getRsvp();


      /**  if(tvRsvpAttendance.toLowerCase().equals("yes")) {
            holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_tick));
            holder.tvRSVP.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            //isRSVP = true;
        } else if(tvRsvpAttendance.toLowerCase().equals("maybe")){
            holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cancel));
            holder.ivTickCancel.setColorFilter(mContext.getResources().getColor(android.R.color.holo_orange_light));
            holder.tvRSVP.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
        }

        else {
            holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cancel));
            holder.tvRSVP.setTextColor(mContext.getResources().getColor(R.color.color_red_rsvp));
            //isRSVP = false;
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
                builder.setTitle("Select option");



                final String[] RSVPOptions = {"Yes", "Maybe", "No"};


                if (eventsModel.getRsvp().toLowerCase().equals("yes")){
                    att =0;
                }else if(eventsModel.getRsvp().toLowerCase().equals("maybe")){
                    att = 1;
                }
                else {
                    att = 2;
                }
                checkedItem = att;

                builder.setSingleChoiceItems(RSVPOptions, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which ==0){
                            dialogOption = 0;
                            holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_tick));
                            holder.ivTickCancel.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
                            holder.tvRSVP.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                            holder.tvAttendance.setText("Attending:"+ " "+ "Yes");
                            isRSVP = true;
                            att = 0;
                            checkedItem = 0;


                        } else if(which == 2){
                            dialogOption = 2;
                            holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cancel));
                            holder.tvRSVP.setTextColor(mContext.getResources().getColor(R.color.color_red_rsvp));
                            holder.ivTickCancel.setColorFilter(mContext.getResources().getColor(R.color.color_red_rsvp));
                            holder.tvAttendance.setText("Attending:"+ " "+"No");
                            isRSVP = false;
                            att = 2;
                            checkedItem =2;
                        } else {
                            dialogOption = 1;
                            holder.ivTickCancel.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cancel));
                            //holder.ivTickCancel.setBackground(mContext.getResources().getColor(android.R.color.holo_orange_light));
                            holder.ivTickCancel.setColorFilter(mContext.getResources().getColor(android.R.color.holo_orange_light));
                            holder.tvRSVP.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
                            isRSVP = false;
                            holder.tvAttendance.setText("Attending:"+ " "+"Maybe");
                            att = 1;
                            checkedItem = 1;
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String status = RSVPOptions[dialogOption];
                        //Toast.makeText(mContext, "" +eventsModel.getEventId()+""+ status, Toast.LENGTH_SHORT).show();
                        RsvpModel rsvpModel = new RsvpModel();
                        rsvpModel.setEventId(eventsModel.getEventId());
                        rsvpModel.setStatus(status);

                        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                        Call<RsvpModel> call = retrofitService.sendRsvpStatus("Bearer" +" "+ accessToken, rsvpModel);
                        call.enqueue(new Callback<RsvpModel>() {
                            @Override
                            public void onResponse(Call<RsvpModel> call, Response<RsvpModel> response) {
                                if(response.isSuccessful()){
                                    //notifyDataSetChanged();
                                    //Toast.makeText(mContext, "Saved successfully!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RsvpModel> call, Throwable t) {
                                //  Toast.makeText(mContext, "failed!", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });  **/

    }

    @Override
    public int getItemCount() {
        return eventsModelList.size();
    }

    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
