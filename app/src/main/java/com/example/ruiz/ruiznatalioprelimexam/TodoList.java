package com.example.ruiz.ruiznatalioprelimexam;

/**
 * Created by Ruiz on 7/22/2017.
 */

public class TodoList {
    private  String title , details , frmdate , todate , frmtime , totime;
    private int priority , ids;

    public  TodoList(){

    }
    public TodoList(int id , String t , String d , String f , String td , String ft , String time , int p){
        this.ids = id;
        this.title = t;
        this.details = d;
        this.frmdate =f;
        this.todate = td;
        this.frmtime = ft;
        this.totime = time;
        this.priority = p;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }
    public int getIds() {
        return ids;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setFrmdate(String frmdate) {
        this.frmdate = frmdate;
    }

    public String getFrmdate() {
        return frmdate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getTodate() {
        return todate;
    }

    public void setFrmtime(String frmtime) {
        this.frmtime = frmtime;
    }

    public String getFrmtime() {
        return frmtime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }

    public String getTotime() {
        return totime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
