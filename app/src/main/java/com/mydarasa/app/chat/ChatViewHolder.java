package com.mydarasa.app.chat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView tvChatPerson, tvChatTime, tvChatMessage;
    RelativeLayout rlChat;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        tvChatPerson = itemView.findViewById(R.id.tvchatName);
        tvChatTime = itemView.findViewById(R.id.tvchatTime);
        tvChatMessage = itemView.findViewById(R.id.tvchatMessage);
        rlChat = itemView.findViewById(R.id.rlChat);
    }
}
