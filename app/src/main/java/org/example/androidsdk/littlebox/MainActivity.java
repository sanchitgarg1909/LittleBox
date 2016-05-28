package org.example.androidsdk.littlebox;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView start_tv,end_tv;
    private RestManager restManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restManager = new RestManager();
        makeApiCall();
        start_tv = (TextView) findViewById(R.id.start_time);
        end_tv = (TextView) findViewById(R.id.end_time);
        findViewById(R.id.set_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog("start"); //TimePicker to choose start time opens up on the first click
            }
        });
    }
    private void makeApiCall() {
        Call<WorkHours> call = restManager.getWorkingHours().getHours("123"); //put user id instead of 123

        call.enqueue(new Callback<WorkHours>() {

            @Override
            public void onResponse(Call<WorkHours> call, Response<WorkHours> response) {
                if (response.isSuccessful()) {
                    WorkHours workHours = response.body();
                    start_tv.setText(workHours.getFrom());
                    end_tv.setText(workHours.getTo());
                }
            }

            @Override
            public void onFailure(Call<WorkHours> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void showTimePickerDialog(final String key) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(key.equals("start")) {
                    showTimePickerDialog("end"); //TimePicker to choose end time opens up after
                    updateStartView(hourOfDay + ":" + minute);
                }else
                    updateEndView(hourOfDay + ":" + minute);
            }
        }, hour, minute, true); //true for 24hr clock
        timePicker.setTitle("Pick "+ key + " time");
        timePicker.show();
    }

    private void updateStartView(String time) {
        start_tv.setText(time);
    }

    private void updateEndView(String time) {
        end_tv.setText(time);
    }

}
