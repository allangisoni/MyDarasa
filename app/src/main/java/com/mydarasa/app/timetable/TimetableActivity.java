package com.mydarasa.app.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import com.mydarasa.app.GetDataService;
import com.mydarasa.app.MyServiceHolder;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RequestNewToken;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.newOkHttpClientInstance;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;
import com.mydarasa.app.student.StudentPickerAdapter;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimetableActivity extends AppCompatActivity implements StudentPickerAdapter.OnItemClickListener {

    private RecyclerView rvtimetable;
    private TimetableAdapter timetableAdapter;
    //MaterialCalendarView calendarView;

    private ProgressBar progressBar;
    List<TimetableItemModel> timetableItemModelList = new ArrayList<>();

    private MultiSnapRecyclerView rvStudentPicker;
    private StudentPickerAdapter studentPickerAdapter;
    List<StudentModel> studentList = new ArrayList<>();
    TextView tvStudName;


    private static final String BASE_URL= "http://173.230.131.197:8086";

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    String selectedDate = " ";

    int dayoftheWeek = 0 ;
    String studentName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        rvtimetable = findViewById(R.id.rvTimetable);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        //calendarView = findViewById(R.id.calendarView);
        rvStudentPicker = findViewById(R.id.rvStudentPicker);
        tvStudName = findViewById(R.id.tvStudName);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Timetable");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);


        studentName = "all";

        tvStudName.setVisibility(View.VISIBLE);

        Calendar startDate = Calendar.getInstance();
        dayoftheWeek = startDate.get(Calendar.DAY_OF_WEEK);
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 11);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                Calendar calendar = date;
                dayoftheWeek = calendar.get(Calendar.DAY_OF_WEEK);
                getTimetable(dayoftheWeek, studentName);
              //  Toast.makeText(TimetableActivity.this, "" + dayoftheWeek, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });

       // List<EventDay> events = new ArrayList<>();

     /**   Calendar calendar = Calendar.getInstance();



        Log.d("date", "" + calendar.get(Calendar.YEAR)+ "-"+ calendar.get(Calendar.MONTH)+"-"+ calendar.get(Calendar.DAY_OF_MONTH) );

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day);


        selectedDate =Integer.toString(day) + "-"+ Integer.toString(month)+"-"+  Integer.toString(year);

       // getTimetable();

        calendarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        CalendarDay calendarDay = CalendarDay.today();
        calendarView.setSelectedDate(CalendarDay.today());
        List<CalendarDay> calendarDays = new ArrayList<>();
        calendarDays.add(calendarDay);
        calendarView.addDecorator(new EventsDecorator(getResources().getColor(R.color.colorAccent), calendarDays));

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month =date.getMonth();
                int day =date.getDay();

                selectedDate = Integer.toString(day) + "-"+ Integer.toString(month)+"-"+  Integer.toString(year);

                //Toast.makeText(TimetableActivity.this, selectedDate, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.VISIBLE);
               getTimetable();

            }
        }); **/



        // events.add(new EventDay(calendar, R.drawable.ic_timetable));
       // calendarView.setEvents(events);

         //   calendarView.setDate(calendar);





        rvtimetable.setHasFixedSize(true);
        timetableAdapter = new TimetableAdapter(this, timetableItemModelList);

        rvtimetable.setAdapter(timetableAdapter);
        rvtimetable.setLayoutManager(new LinearLayoutManager(this));
        rvtimetable.setItemAnimator(new DefaultItemAnimator());

        rvStudentPicker.setHasFixedSize(true);
        studentPickerAdapter = new StudentPickerAdapter(studentList, getApplicationContext(), this);

        rvStudentPicker.setAdapter(studentPickerAdapter);
        MultiSnapRecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStudentPicker.setLayoutManager(layoutManager);
        rvStudentPicker.setItemAnimator(new DefaultItemAnimator());


    }

    public void getTimetable(int weekDay, final String name){
        final String Weekday = Integer.toString(weekDay);

       // Toast.makeText(TimetableActivity.this, "" + dayoftheWeek, Toast.LENGTH_SHORT).show();
        if(timetableItemModelList.size()>0){
            timetableItemModelList.clear();
        }

        MyServiceHolder myServiceHolder = new MyServiceHolder();

        OkHttpClient okHttpClient = new newOkHttpClientInstance.Builder(TimetableActivity.this, myServiceHolder)
                .addHeader("Authorization", "Bearer" +" "+ accessToken)
                .build();

        GetDataService myService = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(GetDataService.class);

        myServiceHolder.set(myService);



        // GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        // Call<TimetableListModel> call = retrofitService.getTimeTable("Bearer" +" "+ accessToken);
        Call<TimetableListModel> call = myService.getTimeTable();
         call.enqueue(new Callback<TimetableListModel>() {
             @Override
             public void onResponse(Call<TimetableListModel> call, Response<TimetableListModel> response) {
                 Log.d("timetableresponse", "" + response.code());
                 Log.d("reportsresponse", "" + response.isSuccessful());

                // Toast.makeText(getApplicationContext(), "Response" + response.code(), Toast.LENGTH_LONG).show();

                 if (response.isSuccessful()) {

                     if (response.body() != null) {

                         TimetableListModel timetableListModel = response.body();

                        // List<TimetableModel> list = new ArrayList<>();
                         if(studentName.equals("all")) {
                           //  Toast.makeText(TimetableActivity.this, "Please select child", Toast.LENGTH_SHORT).show();
                             /**for (TimetableModel model : timetableListModel.getTimetableModel()) {
                                 for (TimetableItemModel itemModel : model.getTimetableItemModel()) {
                                     if(itemModel.getWeekDay().equals(Weekday)){
                                         timetableItemModelList.add(itemModel);
                                     }
                                 }

                             } **/
                         }else{
                             for (TimetableModel model : timetableListModel.getTimetableModel()) {
                                 if(model.getStudentName().equals(name)) {

                                     for (TimetableItemModel itemModel : model.getTimetableItemModel()) {
                                         if (itemModel.getWeekDay().equals(Weekday)) {
                                             timetableItemModelList.add(itemModel);
                                         }
                                     }
                                 }

                                 tvStudName.setText("Viewing"+" "+name +" "+"classes " );

                             }
                         }


                         progressBar.setVisibility(View.INVISIBLE);

                         //List<TimetableItemModel> list = (newlist);

                    /**     List<TimetableItemModel> alltimetableItemModelList = new ArrayList<>();
                         alltimetableItemModelList.addAll(newlist);



                         for (TimetableItemModel item: alltimetableItemModelList){

                             String stringDate = item.getStartTime();
                             Date currdate = new Date();
                             String currentdate = "";
                             try {
                                 Date date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
                                 SimpleDateFormat oFormatter = new SimpleDateFormat("dd-MM-yyyy");
                                 currentdate = selectedDate;
                                 stringDate = oFormatter.format(date);

                                 Date d1 = oFormatter.parse(currentdate);
                                 Date d2 = oFormatter.parse(stringDate);

                                 Log.d("formattedDate", "" + d1);
                                 Log.d("formattedDate", "" + d2);

                                 Calendar cal1 = Calendar.getInstance();
                                 Calendar cal2 = Calendar.getInstance();
                                 cal1.setTime(d1);
                                 cal2.setTime(d2);

                                 if(cal1.equals(cal2)) {
                                    //  timetableItemModelList.add(item);
                                     //Toast.makeText(getApplicationContext(), "size" +timetableItemModelList.size(), Toast.LENGTH_LONG).show();
                                 } else{
                                    // Toast.makeText(getApplicationContext(), "not equal", Toast.LENGTH_LONG).show();
                                 }
                                 //date = oFormatter.format(date);



                             } catch (ParseException e) {
                                 e.printStackTrace();
                             }
                         } **/

                     }

                     timetableAdapter.notifyDataSetChanged();
                 }
             }

             @Override
             public void onFailure(Call<TimetableListModel> call, Throwable t) {
                 progressBar.setVisibility(View.INVISIBLE);
                 Log.d("timetableresponse", "" + t.getMessage());
                  //Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
             }
         });

    }


    public  void getStudents(){

        if(studentList.size()>0){
            studentList.clear();
        }
        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<StudentListModel> call = retrofitService.getStudentDetails("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<StudentListModel>() {
            @Override
            public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {
                Log.d("childresponse", "" + response.code());
                Log.d("childresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        StudentListModel currstudent = response.body();

                        List newlist = new ArrayList<>(Arrays.asList(currstudent.getStudentModel()));

                        studentList.addAll(newlist);

                        if(studentList.size() >0){
                            tvStudName.setText("Select child");
                        }
                        //progressBar.setVisibility(View.INVISIBLE);
                        //StudentModel studentModel = currstudent.getStudentModel();

                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                        //studentList.add(studentModel);
                    }
                } else if(response.code()==401){


                }

                //Log.d("listSize", "" + studentList.size());
                studentPickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudents();
        getTimetable(dayoftheWeek, studentName);
    }

    @Override
    public void onItemClick(StudentModel studentPickerModel, int pos) {
        tvStudName.setVisibility(View.VISIBLE);
        tvStudName.setText(null);
        //tvStudName.setText(studentPickerModel.getName());
        studentName = studentPickerModel.getName();
        progressBar.setVisibility(View.VISIBLE);
        getTimetable(dayoftheWeek, studentName);
    }
}
