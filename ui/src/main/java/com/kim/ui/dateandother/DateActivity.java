package com.kim.ui.dateandother;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kim.ui.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by KIM on 2015/8/14.
 */
public class DateActivity extends Activity implements DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener, View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private TextView dateTv;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private int houerOfDay, minute;
    private int year, monthOfYear, dayOfMonth;
    private ProgressBar progressBarH;
    private Button progressBtnIn;
    private Button progressBtnDe;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.date_main);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        dateTv = (TextView) findViewById(R.id.dateTv);
        progressBarH = (ProgressBar) findViewById(R.id.progressBar_H);
        progressBtnIn = (Button) findViewById(R.id.progressBar_Btn_In);
        progressBtnDe = (Button) findViewById(R.id.progressBar_Btn_De);
        progressBtnIn.setOnClickListener(this);
        progressBtnDe.setOnClickListener(this);

        setProgressBarVisibility(true);
        setProgressBarIndeterminate(true);
        setProgress(3500);

        datePicker.init(2015, 8, 14, this);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setMax(100);
        ratingBar.setProgress(100);
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        String date = "";
//        date += year + "/";
//        date += monthOfYear + "/";
//        date += dayOfMonth + " ";
//        date += timePicker.getCurrentHour() + ":";
//        date += timePicker.getCurrentMinute();
//        dateTv.setText(date);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateTv.setText(format.format(calendar.getTime()));
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        String date = "";
        date += datePicker.getYear() + "/";
        date += datePicker.getMonth() + "/";
        date += datePicker.getDayOfMonth() + " ";
        date += hourOfDay + ":";
        date += minute;
        dateTv.setText(date);
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.timeDialog:
                houerOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(DateActivity.this, new myTimePickerDialog(), houerOfDay, minute, true);
                timePickerDialog.show();
                break;
            case R.id.dateDialog:
                year = calendar.get(Calendar.YEAR);
                monthOfYear = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DateActivity.this, new myDatePlickerDialog(), year, monthOfYear, dayOfMonth);
                datePickerDialog.show();
                break;
            case R.id.progressBar_Btn_In:
                progressBarH.setProgress((int) (progressBarH.getProgress() * 1.2));
                progressBarH.setSecondaryProgress((int) (progressBarH.getSecondaryProgress() * 1.2));
                break;
            case R.id.progressBar_Btn_De:
                progressBarH.setProgress((int) (progressBarH.getProgress() * 0.8));
                progressBarH.setSecondaryProgress((int) (progressBarH.getSecondaryProgress() * 0.8));
                break;
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (rating % 1 >= 0.75) {
            rating = rating + 1 - rating % 1;
        } else if (rating % 1 < 0.75 && rating % 1 >= 0.25) {
            rating = rating + 0.5f - rating % 1;
        } else if (rating % 1 < 0.25) {
            rating = rating - rating % 1;
        }
        Toast.makeText(DateActivity.this, rating + "", Toast.LENGTH_SHORT).show();
        ratingBar.setRating(rating);
    }

    public class myTimePickerDialog implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Toast.makeText(DateActivity.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        }
    }

    public class myDatePlickerDialog implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(DateActivity.this, year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
        }
    }
}
