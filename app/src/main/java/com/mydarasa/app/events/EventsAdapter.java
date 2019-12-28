package com.mydarasa.app.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {

    List<EventsModel> eventsModelList;
    Context mContext;
    Boolean isRSVP = false;

    public EventsAdapter(Context context,List<EventsModel> eventsModelLists ){
        this.mContext = context;
        this.eventsModelList =eventsModelLists;
    }


    @Override
    public EventsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_list, parent, false);

        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventsViewHolder holder, int position) {

        EventsModel eventsModel = eventsModelList.get(position);

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
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
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
            });

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
