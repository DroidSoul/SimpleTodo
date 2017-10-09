package com.codepath.simpletodo;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Products {

    String priority;
    String _productname;
    long time;


    public Products() {

    }

    public Products(String productname, String priority, long time)
    {
        this._productname = productname;
        this.priority = priority;
        this.time = time;
    }

    public void set_priority(String priority) {
        this.priority = priority;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }

    public String get_priority() {
        return priority;
    }

    public String get_productname() {
        return _productname;
    }
    public void set_time(long time) {
        this.time = time;
    }
    public long get_time() {
        return time;
    }
    public String get_timeString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(get_time()));
    }
}
