package com.example.ruiz.ruiznatalioprelimexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_add;
    ListView lstTitle;

    private CustimListAdapter adapter;
    Cursor cursor;
    DBTools dbTools;
    Intent data_Intent;
    ArrayList<TodoList> todo = new ArrayList<TodoList>();


    public int ids = 0;
    public  int get_id =1;

    DialogInterface.OnClickListener dialoglistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = (Button) findViewById(R.id.btn_AddNote);

        lstTitle = (ListView)findViewById(R.id.lsttitle);
        lstTitle.setOnItemLongClickListener(new ListViewItemLongClick());
        lstTitle.setOnItemClickListener(new ListItemClick());

        adapter = new CustimListAdapter(this ,todo);
        lstTitle.setAdapter(adapter);

        dbTools = new DBTools(getApplicationContext());
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , SecondActivity.class);
                startActivityForResult(intent , 1);
            }
        });
        dialoglistener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i)
                {
                    case DialogInterface.BUTTON_POSITIVE :
                        deleteUser();
                      // Toast.makeText(MainActivity.this, "Item successfully deleted", Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //Toast.makeText(MainActivity.this, "Item was not added", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        LoadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                LoadData();
            }
        }else if(requestCode == 2) {
            if (resultCode == 2) {
                LoadData();
            }
        }else if(requestCode == 3 ){
            if(resultCode == 3){
                // todo.remove(get_id);
            }
        }
        LoadData();
    }
    public void deleteUser(){
        dbTools.delete(ids);
        todo.clear();
        LoadData();
        Toast.makeText(MainActivity.this , "Item successfully deleted" , Toast.LENGTH_SHORT).show();
    }
    private void LoadData(){
        todo.clear();
        cursor = dbTools.executeQuery();
        if (cursor.moveToFirst()) {
            do {
                int str_id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String frmdate = cursor.getString(3);
                String todate = cursor.getString(5);
                String frmtime = cursor.getString(4);
                String totime = cursor.getString(6);
                int seek = cursor.getInt(7);
                todo.add(new TodoList(str_id, title, details, frmdate, todate, frmtime, totime, seek));
            }
            while (cursor.moveToNext());
            lstTitle.setAdapter(adapter);
        } else {
            //Toast.makeText(getApplicationContext(), "No Record", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public class ListViewItemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            TodoList e = todo.get(i);
            ids = e.getIds();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to delete?").setPositiveButton("Yes", dialoglistener).setNegativeButton("No", dialoglistener).show();
            return true;
        }
    }
    public class ListItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TodoList e = todo.get(i);
            get_id = e.getIds();
            int id1 = get_id;
            Intent intent = new Intent(getBaseContext(), SecondActivity.class);
            intent.putExtra("get_id", id1);
            startActivityForResult(intent , 2);
        }
    }

}
