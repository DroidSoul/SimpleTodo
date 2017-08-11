package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;
public class EditItemActivity extends AppCompatActivity {
    int position;
    EditText editItem;
    EditText editPriority;
    DatePicker datePicker;
//    int xxday, xxmonth, xxyear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editItem = (EditText)findViewById(R.id.editItem);
        editPriority = (EditText)findViewById(R.id.editPriority);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        position = getIntent().getIntExtra("pos", 0);
        String str1 = getIntent().getStringExtra("itemContent");
        String str2 = getIntent().getStringExtra("itemPriority");
        long time = getIntent().getLongExtra("itemDuedate", 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis (time);
        int xxday = calendar.get(Calendar.DATE);
        int xxmonth = calendar.get(Calendar.MONTH);
        int xxyear = calendar.get(Calendar.YEAR);
       datePicker.init(xxyear, xxmonth, xxday, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
//        datePicker.init(xxyear, xxmonth, xxday, null);

        editItem.setText(str1);
        editItem.setSelection(str1.length());
        editPriority.setText(str2);
        editPriority.setSelection(str2.length());
    }
    public void saveItem(View v) {
//        EditText editItem = (EditText)findViewById(R.id.editItem);
//        Toast.makeText(this, datePicker.getDayOfMonth()+"", Toast.LENGTH_LONG).show();
        Intent i = new Intent();
        i.putExtra("updatedItem", editItem.getText().toString());
        i.putExtra("updatedPriority", editPriority.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        long dueTime = calendar.getTimeInMillis();
//        long dueTime = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()).getTime();
//        long dueTime = new Date(xxyear, xxmonth, xxday).getTime();
        i.putExtra("updatedDuedate", dueTime);
        i.putExtra("code", position);
        setResult(RESULT_OK, i);
        finish();
    }
/*    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            xxyear = selectedYear;
            xxmonth = selectedMonth;
            xxday = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            datePicker.init(xxyear, xxmonth, xxday, null);

        }
    };*/
}
