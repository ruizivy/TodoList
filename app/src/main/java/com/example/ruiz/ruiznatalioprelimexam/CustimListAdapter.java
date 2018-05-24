package com.example.ruiz.ruiznatalioprelimexam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ruiz on 7/22/2017.
 */

public class CustimListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<TodoList> todoLists;
    public  ImageView img;


    public  CustimListAdapter(Activity  a , List<TodoList> t){

        this.activity = a;
        this.todoLists = t;
    }
    public  int getCount(){
        return  todoLists.size();
    }
    public Object getItem(int pos){
        return  todoLists.get(pos);
    }
    public long getItemId(int pos){
        return  pos;
    }
    public View getView(int pos , View view , ViewGroup vGroup){
        if(inflater == null){
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        TodoList e = todoLists.get(pos);
        int priority = e.getPriority();

        if(view == null){
            view = inflater.inflate(R.layout.activity_list_item , null);
          img  = (ImageView) view.findViewById(R.id.imgpic);
        }
        if(priority == 1){
            view.setBackgroundColor(Color.parseColor("#FDFF93"));
            // img.setBackgroundResource(R.drawable.yellow);
            img.setImageResource(R.drawable.yellow);
        }else if(priority == 2){
            view.setBackgroundColor(Color.parseColor("#FFB900"));
            // img.setBackgroundResource(R.drawable.orange);
            img.setImageResource(R.drawable.orange);
        }else if(priority == 3){
            view.setBackgroundColor(Color.parseColor("#EF6950"));
            img.setImageResource(R.drawable.red);
        }else if(priority == 0){
            view.setBackgroundColor(Color.WHITE);
            // img.setBackgroundResource(R.drawable.gray);
            img.setImageResource(R.drawable.gray);
        }

        TextView title = (TextView) view.findViewById(R.id.lbltitle);
        TextView details = (TextView) view.findViewById(R.id.lbldetails);
        TextView frmdate = (TextView) view.findViewById(R.id.lblfrmdate);
        TextView todate = (TextView) view.findViewById(R.id.lbltodate);
        title.setText(e.getTitle());
        details.setText(e.getDetails());
        frmdate.setText(e.getFrmdate());
        todate.setText(e.getTodate());

        return view;
    }

}
