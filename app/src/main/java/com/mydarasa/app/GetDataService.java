package com.mydarasa.app;

import com.mydarasa.app.cocurricular.CocurricularListModel;
import com.mydarasa.app.events.EventListModel;
import com.mydarasa.app.fees.FeeStatementModel;
import com.mydarasa.app.progressreports.ProgressRListModel;
import com.mydarasa.app.guardian.GuardianData;
import com.mydarasa.app.login.UserLoginModel;
import com.mydarasa.app.refreshtoken.RefreshTokenModel;
import com.mydarasa.app.register.GuardianModel;
import com.mydarasa.app.student.StudentInfoModel;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.timetable.TimetableListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataService {



  /**  @Headers("Content-Type: application/json")
    @POST("api/v1/auth/login")
    Call<UserLoginModel> isValidUser(
            @Header("Authorization") String authkey,
            @Body UserLoginModel userLoginModel
    );

            **/

  @POST("/api/v1/auth/login")
  Call<UserLoginModel> isValidUser(
          @Body UserLoginModel userLoginModel

  );

  @Headers("Content-Type: application/json")
  @POST("/api/v1/guardians")
  Call<GuardianModel> registerGuardian(
          @Body GuardianModel guardianModel
  );

  @GET("/api/v1/students?schoolId=1")
  Call<StudentListModel> getStudentDetails(
          @Header("Authorization") String authHeader
         // @Path("sch_Id") long schoolId

  );

  @GET("/api/v1/events?schoolId=1")
  Call<EventListModel> getEvents(
          @Header("Authorization") String authHeader
  );

  @GET("/api/v1/guardians/{id}")
  Call<GuardianData> getGuardian(
          @Header("Authorization") String authHeader,
          @Path("id") long id
  );

  @POST("/api/v1/auth/refresh-token")
   Call<RefreshTokenModel> getRefreshToken(
           @Body RefreshTokenModel refreshTokenModel
  );

  @POST("/api/v1/students/student-drilldown/{id}")
  Call<StudentInfoModel> getStudentEvents(
          @Header("Authorization") String authHeader,
          @Path("id") long id
  );

  @GET("/api/v1/co-curricular?schoolId=1")
  Call<CocurricularListModel> getCocuricularActivities(
          @Header("Authorization") String authHeader
  );

  @GET("/api/v1/alerts?schoolId=1")
  Call<EventListModel> getAlerts (
          @Header("Authorization") String authHeader
  );

  @GET("/api/v1/progress-report?schoolId=1")
  Call<ProgressRListModel> getProgressReports(
          @Header("Authorization") String authHeader
  );

  @GET("/api/v1/time-table?schoolId=1")
  Call<TimetableListModel> getTimeTable(
          @Header("Authorization") String authHeader
  );

  @GET("/api/v1/fee-payment/{id}")
  Call<FeeStatementModel> getFeeStatement(
          @Header("Authorization") String authHeader,
          @Path("id") long id
  );
}