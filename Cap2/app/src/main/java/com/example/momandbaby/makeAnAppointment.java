package com.example.momandbaby;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class makeAnAppointment extends AppCompatActivity {
    private EditText timeEditText, date;

    public void showAlertEditOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Đặt lịch thành công! Vui lòng chờ xác nhận từ bác sĩ")
                .setTitle("Thông báo!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showAlertEdit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn đặt lịch không?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code xử lý khi người dùng chọn Có
                        showAlertEditOk();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code xử lý khi người dùng chọn Không
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_an_appointment);
        timeEditText = findViewById(R.id.timeEditText);
    }


    public void selectDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Xử lý sự kiện khi người dùng chọn ngày tháng
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                date.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void selectTime(View view) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Xử lý sự kiện khi người dùng chọn giờ
                String selectedTime = hourOfDay + ":" + minute;
                timeEditText.setText(selectedTime);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

}