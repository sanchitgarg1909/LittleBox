package org.example.androidsdk.littlebox;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWorkingHours {

    @GET("/getworkinghours")
    Call<WorkHours> getHours(@Query("user_id") String userId);
}
