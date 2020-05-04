package com.hanu.students.repository.remote.api;

import com.hanu.students.model.Article;
import com.hanu.students.model.ClassInformation;
import com.hanu.students.model.Grades;
import com.hanu.students.model.Profile;
import com.hanu.students.model.Registration;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.model.TuitionFee;
import com.hanu.students.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiConnector {
//    String PREFIX = "/mocking/api/v1/links/ca01d34d-d813-40c9-9ea7-82aec6514ccc/v1";
    final String PREFIX = "";

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/articles")
    Call<Article> getArticle();

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/articles")
    Call<List<Article>> getArticles();

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/timetable")
    Call<List<TimetableUnit>> getTimetable(@Query("authToken") String authToken); // will be List<TimetableUnit>

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/class")
    Call<ClassInformation> getClassInformation(@Query("classCode") String classCode,
                                               @Query("authToken") String authToken,
                                               @Query("semester") Integer semester);

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/grades")
    Call<List<Grades>> getGrades(@Query("authToken") String authToken);

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/tuition")
    Call<List<TuitionFee>> getTuition(@Query("authToken") String authToken);

    @Headers({"Accept:*/*"})
    @POST(PREFIX + "/tuition")
    Call<ResponseBody> postTuitionFee(@Body List<TuitionFee> tuitionList,
                                      @Query("authToken") String authToken);

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/profile")
    Call<Profile> getProfile(@Query("authToken") String authToken);

    @Headers({"Accept:*/*"})
    @POST(PREFIX + "/session")
    Call<String> startSession(@Body User user);

    @Headers({"Accept:*/*"})
    @DELETE(PREFIX + "/session")
    Call<ResponseBody> terminateSession(@Query("authToken") String authToken);

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/registrations/available")
    Call<List<RegistrationClass>> getAvailableClasses(@Query("authToken") String authToken,
                                                @Query("semester") int semesterId);

    @Headers({"Accept:*/*"})
    @GET(PREFIX + "/registrations")
    Call<List<Registration>> getRegisteredClassesThisSemester(@Query("authToken") String authToken,
                                                        @Query("semester") int semesterId);

    @Headers({"Accept:*/*"})
    @HTTP(method= "POST", path = PREFIX + "/registrations", hasBody = true)
    Call<ResponseBody> addNewRegistrations(@Query("authToken") String authToken,
                                           @Body List<Registration> registration);

    @Headers({"Accept:*/*"})
    @HTTP(method= "DELETE", path = PREFIX + "/registrations", hasBody = true)
    Call<ResponseBody> removeRegistrations(@Query("authToken") String authToken,
                                           @Body List<Registration> registration);
}
