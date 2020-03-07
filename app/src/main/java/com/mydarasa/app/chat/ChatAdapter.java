package com.mydarasa.app.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.mydarasa.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private Context mContext;
    private List<ChatModel> chatList;

    public ChatAdapter(Context context,List<ChatModel> chatList){
        this.mContext =context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_list, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
     ChatModel model = chatList.get(position);
        SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = iFormatter.parse(model.getChatTime());
        }catch (Exception e){

        }
        long timeInMillis = date.getTime();
        String time = TimeAgo.using(timeInMillis);

     holder.tvChatPerson.setText(model.getChatPerson());
     holder.tvChatTime.setText(time);
     holder.tvChatMessage.setText(model.getComment());

     if(holder.tvChatPerson.getText().toString().toLowerCase().equals("you")){
         holder.rlChat.setBackgroundColor(mContext.getResources().getColor(R.color.chat_color));
     }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
