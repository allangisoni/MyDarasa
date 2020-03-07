package com.mydarasa.app.chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.alerts.AlertsListModel;
import com.mydarasa.app.alerts.AlertsModel;
import com.mydarasa.app.alerts.PostAlertChatItem;
import com.mydarasa.app.fees.CreatePaymentActivity;
import com.mydarasa.app.progressreports.ProgressModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    RecyclerView rvChat;
    ImageView sendButton;
    EditText messageArea;
    List<ChatModel> chatList = new ArrayList<>();
    private ChatAdapter chatAdapter;

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";
    ChatModel[] chats;
    AlertsModel alertsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvChat = findViewById(R.id.rvChat);
        myToolbar =  findViewById(R.id.toolbar);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);


        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Comments");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        rvChat.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(this, chatList);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(chatAdapter);
        rvChat.setItemAnimator(new DefaultItemAnimator());


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            String gsonString = bundle.getString("gsonString");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

             alertsModel = new AlertsModel();
            alertsModel = gson.fromJson(gsonString, AlertsModel.class);

           chats = alertsModel.getChatModel();
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!messageArea.getText().toString().equals("")){
                    PostAlertChatItem item = new PostAlertChatItem();
                    item.setAlertNo(alertsModel.getAlertNo());
                    item.setComments(messageArea.getText().toString());
                    addComment(item);
                    Date date = new Date();
                    SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String newDate = iFormatter.format(date);
                    ChatModel model = new ChatModel("You", newDate, item.getComments());
                    chatList.add(model);
                    chatAdapter.notifyDataSetChanged();

                    messageArea.setText("");
                }

            }
        });
        getChats();
    }

    private  void getChats(){

        if (chatList.size() > 0){
            chatList.clear();
        }

         for (ChatModel chatModel : chats){
             if(chatModel != null) {
             String chatPerson = chatModel.getChatPerson();
             String person = "";
                 if (chatPerson.equals("1")) {
                     person = "You";
                 } else {
                     person = "School Admin";
                 }
                 ChatModel model = new ChatModel(person, chatModel.getChatTime(), chatModel.getComment());
                 chatList.add(model);
             }
         }

        /**ChatModel model = new ChatModel("Benitto Muuo", "20min ago", "Lorem ipsum dolor sit amet," +
                " consectetur adipiscing elit. Ut sed justo et dui maximus efficitur eu et ex. " );

        ChatModel model1 = new ChatModel("Allan Hesus", "10min ago", "Lorem ipsum dolor sit amet," +
                " consectetur adipiscing elit. Ut sed justo et dui maximus efficitur eu et ex. ");

        chatList.add(model);
        chatList.add(model1);
        chatList.add(model);
        chatList.add(model);
        chatList.add(model1);
        chatList.add(model);
        chatList.add(model1);
        chatList.add(model);
        chatList.add(model1);
        chatList.add(model);
        chatList.add(model1);

       **/
        chatAdapter.notifyDataSetChanged();
    }

    public  void addComment(PostAlertChatItem comment){

        PostAlertChatItem postAlertChatItem = new PostAlertChatItem();
        postAlertChatItem = comment;

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PostAlertChatItem> call = retrofitService.postAlertComment("Bearer" +" "+ accessToken, postAlertChatItem);

        call.enqueue(new Callback<PostAlertChatItem>() {
            @Override
            public void onResponse(Call<PostAlertChatItem> call, Response<PostAlertChatItem> response) {
                if(response.isSuccessful()){

              // Toast.makeText(ChatActivity .this, "Payment Uploaded Successfully", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<PostAlertChatItem> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
