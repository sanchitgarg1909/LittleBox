package org.example.androidsdk.littlebox;

import org.example.androidsdk.littlebox.model.WorkHours;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWorkingHours {

    @GET("/gethours")
    Call<WorkHours> getHours(@Query("user_id") String userId);
}
