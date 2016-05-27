package org.example.androidsdk.littlebox;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView start_tv,end_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_tv = (TextView) findViewById(R.id.start_time);
        end_tv = (TextView) findViewById(R.id.end_time);
        findViewById(R.id.set_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog("start"); //TimePicker to choose start time opens up on the first click
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
