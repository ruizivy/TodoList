package com.example.ruiz.ruiznatalioprelimexam;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private List<TodoList> todes = new ArrayList<TodoList>();

    TextView low , meduim , high;
    EditText ettitle , etdetails;
    Button btnfrmdate , btnfrmtime, btntodate, btntotime , btnSave, btn_cancel;
    SeekBar seekBar;
    DialogInterface.OnClickListener dialoglistener;
    Cursor cursor;
    DBTools dbTools;
    Intent dataIntent;
    int Year , toYear;
    int Month , toMonth;
    int Day , toDay , Hour , toHour, Minute, toMinute;


    static final int FROM_DATE_DIALOG = 0;
    static final int TO_DATE_DIALOG = 1;
    static final int FROM_TIME_DIALOG =2;
    static final int TO_TIME_DIALOG =3;
    int seekvalue =0;
    public  int data_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ettitle = (EditText) findViewById(R.id.txttitle);
        etdetails = (EditText)findViewById(R.id.txtdetails);

        low = (TextView)findViewById(R.id.lbllow);
        meduim =(TextView)findViewById(R.id.lblmedium);
        high = (TextView) findViewById(R.id.lblhigh);

        btnfrmdate = (Button)findViewById(R.id.btnfrmdate);
        btnfrmdate.setOnClickListener(new MyButtonEvent());
        btnfrmtime = (Button)findViewById(R.id.btnfrmtime);
        btnfrmtime.setOnClickListener(new MyButtonEvent());

        btntodate = (Button)findViewById(R.id.btntodate);
        btntodate.setOnClickListener(new MyButtonEvent());
        btntotime = (Button)findViewById(R.id.btntotime);
        btntotime.setOnClickListener(new MyButtonEvent());

        btnSave = (Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new MyButtonEvent());
        btn_cancel = (Button)findViewById(R.id.btn_Cancel);
        btn_cancel.setOnClickListener(new MyButtonEvent());

        seekBar = (SeekBar)findViewById(R.id.sbpriority);
        seekBar.setOnSeekBarChangeListener(new SeekBarHandle());
        seekBar.setMax(3);
        dbTools = new DBTools(getApplicationContext());

        final Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        toYear = c.get(Calendar.YEAR);
        toMonth = c.get(Calendar.MONTH);
        toDay = c.get(Calendar.DAY_OF_MONTH);
        Hour = c.get(Calendar.HOUR_OF_DAY);
        Minute = c.get(Calendar.MINUTE);
        toHour = c.get(Calendar.HOUR_OF_DAY);
        toMinute = c.get(Calendar.MINUTE);

        updateToDateDisplay();
        updateDisplayfrmDate();
        updatefrmTimeDisplay();
        updateToTimeDisplay();
        Intent intent = getIntent();
        int id = intent.getIntExtra("get_id" , 2);
        data_id = id;
        DisplayData();
    }

    public  void InsertData(){
        String title = ettitle.getText().toString();
        String details  = etdetails.getText().toString();
        String frmdate = btnfrmdate.getText().toString();
        String todate = btntodate.getText().toString();
        String frmtime = btnfrmtime.getText().toString();
        String totime = btntotime.getText().toString();
        int seek = seekvalue;
        if(title.trim().length() != 0  && details.trim().length() != 0 && frmdate.trim().length() != 0 && frmtime.trim().length() !=0 &&
                todate.trim().length() != 0 && totime.trim().length() != 0){

            dbTools.insertUser(title, details, frmdate , frmtime , todate, totime , seek);
            setResult(0 , dataIntent);
            finish();
            Toast.makeText(getApplicationContext() , "Item successfully save" , Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(SecondActivity.this, "Please complete the requirements" , Toast.LENGTH_SHORT).show();
        }
    }

    public  class MyButtonEvent implements View.OnClickListener{
        public  void onClick(View v){
            if(v == btnSave) {
                if(btnSave.getText().toString().equals("SAVE")) {
                    InsertData();
                }
                else {
                    UpdateData();
                }
            }
            else if(v == btn_cancel){
                if(btn_cancel.getText().toString().equals("CANCEL")) {
                    setResult(2, dataIntent);
                    finish();
                }
            }else if(v == btnfrmdate){
                showDialog(FROM_DATE_DIALOG);
               updateDisplayfrmDate();
            }else if(v == btntodate){
                showDialog(TO_DATE_DIALOG);
                updateToDateDisplay();
            }else if(v == btnfrmtime){
                showDialog(FROM_TIME_DIALOG);
                updatefrmTimeDisplay();

            }else if(v == btntotime){
                showDialog(TO_TIME_DIALOG);
                updateToTimeDisplay();
            }
        }
    }
    private void updateDisplayfrmDate() {
            btnfrmdate.setText(new StringBuilder().append(Month + 1).append("-").append(Day).append("-")
                    .append(Year).append(" "));
    }
    private  void updatefrmTimeDisplay(){
        btnfrmtime.setText(new StringBuilder().append(pad(Hour)).append(":").append(pad(Minute)));
    }
    private  void updateToDateDisplay(){
        btntodate.setText(new StringBuilder().append(toMonth + 1).append("-").append(toDay).append("-")
                .append(toYear).append(" "));
    }
    private void updateToTimeDisplay(){
        btntotime.setText(new StringBuilder().append(pad(toHour)).append(":").append(pad(toMinute)));
    }

    private static String pad(int c){
        if(String.valueOf(c).length() > 2)
        {
            return  String.valueOf(c);
        }
        else if(String.valueOf(c).length() == 1)
            return "0" + String.valueOf(c);
        else
            return  String.valueOf(c);
    }
    private DatePickerDialog.OnDateSetListener frmdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            Year = year;
            Month = month;
            Day = dayOfMonth;
            updateDisplayfrmDate();
        }
    };
    private TimePickerDialog.OnTimeSetListener frmtimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Hour = hourOfDay;
            Minute = minute;
            updatefrmTimeDisplay();
        }
    };
    private DatePickerDialog.OnDateSetListener todateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            toYear = year;
            toMonth = month;
            toDay = dayOfMonth;
            updateToDateDisplay();
        }
    };
    private TimePickerDialog.OnTimeSetListener totimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            toHour = hourOfDay;
            toMinute = minute;
            updateToTimeDisplay();
        }
    };
    protected Dialog onCreateDialog(int id){
        switch (id) {
            case FROM_DATE_DIALOG :
                return new DatePickerDialog(this, frmdateListener, Year, Month, Day);
            case TO_DATE_DIALOG :
                return new DatePickerDialog(this, todateListener , Year , Month, Day);
            case FROM_TIME_DIALOG:
                return new TimePickerDialog(this, frmtimeListener , Hour , Minute, false);
            case TO_TIME_DIALOG:
                return new TimePickerDialog(this, totimeListener , Hour , Minute , false);
        }
        return null;
    }
    private class SeekBarHandle implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            seekvalue = i;
            if(i == 1)
            {
                low.setTextColor(Color.parseColor("#FDFF93"));
               // meduim.setTextColor(Color.parseColor("#FFFF"));
                //high.setTextColor(Color.parseColor("#FFFF"));
            }else if( i == 2){
               // low.setTextColor(Color.parseColor("#FFFF"));
                meduim.setTextColor(Color.parseColor("#FFB900"));
               // high.setTextColor(Color.parseColor("#FFFF"));
            }else if(i ==3){
                //low.setTextColor(Color.parseColor("#FFFF"));
                //meduim.setTextColor(Color.parseColor("#FFFF"));
                high.setTextColor(Color.parseColor("#EF6950"));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
    public void DisplayData(){
        cursor = dbTools.getUser(data_id);
        if(cursor.moveToFirst()){
            do{
                int str_id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String frmdate = cursor.getString(3);
                String todate = cursor.getString(5);
                String frmtime = cursor.getString(4);
                String totime = cursor.getString(6);
                int seek = cursor.getInt(7);

                ettitle.setText(title);
                etdetails.setText(details);
                btnfrmdate.setText(frmdate);
                btnfrmtime.setText(frmtime);
                btntodate.setText(todate);
                btntotime.setText(totime);
                seekBar.setProgress(seek);
                btnSave.setText("UPDATE");
            }
            while(cursor.moveToNext());
        }
        else {
            btnSave.setText("SAVE");
        }

    }
    public void UpdateData(){
        String title = ettitle.getText().toString();
        String details  = etdetails.getText().toString();
        String frmdate = btnfrmdate.getText().toString();
        String todate = btntodate.getText().toString();
        String frmtime = btnfrmtime.getText().toString();
        String totime = btntotime.getText().toString();
        int seek = seekvalue;
        if(title.trim().length() != 0  && details.trim().length() != 0 && frmdate.trim().length() != 0 && frmtime.trim().length() !=0 &&
                todate.trim().length() != 0 && totime.trim().length() != 0){
            dataIntent = getIntent();
            dbTools.updateUser(data_id , title, details , frmdate ,frmtime , todate , totime ,seek);
            setResult(2 , dataIntent);
            finish();
            Toast.makeText(getApplicationContext() , "Item successfully updated" , Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(SecondActivity.this, "Please complete the requirements" , Toast.LENGTH_SHORT).show();
        }
    }

}
