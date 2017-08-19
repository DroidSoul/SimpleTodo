package com.codepath.simpletodo;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.onFragmentResult, MyAlertDialogFragment.isYes {
    ArrayList<Products> items;
    UsersAdapter itemsAdapter;
    ListView lvItems;
    MyDBHandler dbHandler;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        dbHandler = new MyDBHandler(this, null, null, 3);
        readItems();
        itemsAdapter = new UsersAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }
    //listener for additem
    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(new Products(itemText, "1", System.currentTimeMillis()));
        etNewItem.setText("");
        acitivtyToFragment(items.size() - 1);
    }
    //listeners for listview
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener (
                new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        pos = position;
                        FragmentManager fm = getSupportFragmentManager();
                        MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance("alert");
                        alertDialog.show(fm, "fragment_alert");
                        return true;
                    }

        });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        acitivtyToFragment(position);
                    }
                }
        );

    }
    //read data from database
    private void readItems() {
        items = dbHandler.databaseToList();
    }
    // update database
    private void writeItems() {
        dbHandler.deleteAll();
        for (Products p : items) {
            dbHandler.addProduct(p);
        }
    }
    // implement callback method of dialog fragment to get data
    @Override
    public void returnData(String body, String priority, long time, int code) {
        items.set(code, new Products(body, priority, time));
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }
    //send data from activity to dialog fragment
    public void acitivtyToFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putString("itemContent", items.get(position).get_productname());
        bundle.putString("itemPriority", items.get(position).get_priority());
        bundle.putLong("itemDuedate", items.get(position).get_time());
        EditItemDialogFragment fragobj = EditItemDialogFragment.newInstance("title");
//      EditItemDialogFragment fragobj = new EditItemDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        fragobj.setArguments(bundle);
        fragobj.show(fm, "fragment_edit_item");
    }
    //implement callabck method of alert dialog fragment
    @Override
    public void clickYes(String str) {
        if (str.equals("yes")) {
            items.remove(pos);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
    //sort list based on due date
    public void sortTime(View view) {
        Collections.sort(items, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                if (o1.time <= o2.time) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        });
        itemsAdapter.notifyDataSetChanged();
    }
    //sort list based on priority
    public void sortPriority(View view) {
        Collections.sort(items, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                if (Integer.valueOf(o1.priority) <= Integer.valueOf(o2.priority)) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        });
        itemsAdapter.notifyDataSetChanged();
    }
}
