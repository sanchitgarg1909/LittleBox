package org.example.androidsdk.littlebox;

import org.example.androidsdk.littlebox.model.Success;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SetWorkingHours {

    @FormUrlEncoded
    @POST("/sethours")
    Call<Success> setHours(@Field("user_id") String userId, @Field("start_time") String start, @Field("end_time") String end);
}
