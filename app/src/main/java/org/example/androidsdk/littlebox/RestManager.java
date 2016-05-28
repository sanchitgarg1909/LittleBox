package org.example.androidsdk.littlebox;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {
    private GetWorkingHours getWorkingHours;
    OkHttpClient client;

    public GetWorkingHours getWorkingHours() {
        client = new OkHttpClient();
        if (getWorkingHours==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            getWorkingHours = retrofit.create(GetWorkingHours.class);
        }
        return getWorkingHours;
    }
}
