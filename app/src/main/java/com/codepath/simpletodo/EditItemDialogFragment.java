package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;


public class EditItemDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    int position;
    EditText editItem;
    DatePicker datePicker;
    onFragmentResult activityCommander;
    String spinnerPriority;
    String[] choices = {"1", "2", "3"};

    // get user selected value from spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerPriority = choices[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //inteface for callback method
    public interface onFragmentResult {
        void returnData(String body, String priority, long time, int code);
    }


    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            activityCommander = (onFragmentResult) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    public EditItemDialogFragment() {

    }

    public static EditItemDialogFragment newInstance(String title) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        editItem = (EditText)view.findViewById(R.id.editItem);
        datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        position = getArguments().getInt("pos", 0);
     //get data from activity
        String str1 = getArguments().getString("itemContent");
        String str2 = getArguments().getString("itemPriority");
        long time = getArguments().getLong("itemDuedate", 0);
     //initiate datepicker with original date
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
        //set item anme with original name and move cursor to the end
        editItem.setText(str1);
        editItem.setSelection(str1.length());
       // set adapter to spinner and initiate spinner value with orignal priority
        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,choices);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setSelection(Integer.valueOf(str2) - 1);
        return view;
    }
    // save update
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button saveBtn = (Button) view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        saveItem(v);
                    }
                }
        );
    }
    //return data to activity with callback method and return to activity
    public void saveItem(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        long dueTime = calendar.getTimeInMillis();
        activityCommander.returnData(editItem.getText().toString(), spinnerPriority, dueTime, position);
        dismiss();
    }

}
